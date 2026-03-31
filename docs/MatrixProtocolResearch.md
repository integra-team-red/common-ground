# Matrix Protocol
- https://matrix.org/docs/matrix-concepts/elements-of-matrix/
- https://spec.matrix.org/latest/
- communication protocol used for IM (instant messaging)
- implemented through HTTP + JSON APIs

## Elements of Matrix
### Homeservers & Users
- Matrix servers are responsible for hosting user accounts, storing and serving rooms history, connect with other
homeservers (federation through the defined Server-Server API) etc.
- users on a homeserver with domain _example.com_ have identifier: `@username:example.com`
- each user ID can have multiple **devices** (ex phone, laptop, web session), each with an ID and name
- when user logs in, an access token is generated and associated with a specific device

### Rooms
- equivalent with "chats"
- have a unique identifier: `!room:example.com`
- rooms do not reside on the specified domain, users can join rooms from other homeservers by keeping a local copy of
the room in their own homeserver
- can have human-readable aliases associated to it: `#alias:example.com`
- users can send events into rooms = **json objects** describing what user wants to do (join room, send message, etc.)
- a collection of rooms can be contained in a **space** = special type of room to organize other rooms into a hierarchy

#### Power Levels
- users have "roles"/"power levels" in a room:
- 100: administrator (owner/creator of the room)
- 50: moderator
- 0: default user

## Setup Notes
### Synapse Configuration
- [_Synapse_](https://github.com/element-hq/synapse) is a Matrix homeserver implementation, developed by _Element_
- recommended to use _PostgreSQL_ for production
- backend talks with _Synapse_ using the implemented APIs:
    - the Client-Server API defined in the specifications of the Matrix protocol: communication between apps and the
    homeserver
    - Synapse Admin API specific to _Synapse_
- [configuration file](https://element-hq.github.io/synapse/latest/usage/configuration/config_documentation.html) for
backend-only use, `homeserver.yaml`:

```yaml
server_name: "<homeserver_name>"                # domain name
listeners:                                      # optional (defaults to port 8008 with client & federation)
  - port: <homeserver_port>
    type: http                                  # https in production
    resources:
      - names: [client]                         # exposes Client-Server API
database:
  name: psycopg2                                # PostgreSQL
  args:
    user: <synapse_user>
    password: <synapse_password>
    dbname: <synapse_database>
    host: localhost
    port: <port>
registration_shared_secret: "<shared_secret>"   # used for HMAC
enable_registration: false                      # disable public registration, only create accounts in backend
```

### Element Web Configuration
- [_Element_](https://element.io/en) is a Matrix client, only the frontend, which talks with _Synapse_ for displaying
the current state to the user
- if self-hosting _Element Web_, connect it to _Synapse_ homeserver in `config.json`:

```json
{
    "default_server_config": {
        "m.homeserver": {
            "base_url": "https://localhost:<port>",
            "server_name": "<homeserver_name>"
        }
    },
    "disable_custom_urls": true,      // lock Element to local Synapse instance
    "roomDirectory": {                // limit room discovery to local homeserver
        "servers": ["<homeserver_name>"]
    }
}
```

## APIs Usage
- in this section, the domain name _example.com_ in the identifiers will be namespaced using `m`
- Synapse Admin API endpoints start with: `/_synapse/admin/v1/` $\rightarrow$ uses admin access token for
authentications
- Matrix Client-Server API endpoints start with: `/_matrix/client/v3/`

|                                | Client-Server API  | Synapse Admin API         |
|--------------------------------|--------------------|---------------------------|
| Works on                       | any homeserver     | only Synapse              |
| Represents                     | a user             | the server owner          |
| Respects Matrix protocol rules | yes                | no (overrides some rules) |

### Error Format

```json
{
    "errcode": "<error_code>",
    "error": "<error_message>"
}
```
- error code: `M_CODE` (ex: `M_FORBIDDEN`, `M_MISSING_TOKEN`, `M_BAD_JSON` more at
[Common error codes](https://spec.matrix.org/v1.18/client-server-api/#common-error-codes))

### Authentication
#### Register
- create a user using username and password on _Synapse_ for backend account creation
- not using Client-Server endpoint because it uses only User-Interactive Authentication API (involves captchas, email
verification etc.), designed for users self-registering
- also logs in the user and receive access token to use for following requests

Steps:
1. Request a nonce from the homeserver: `GET /_synapse/admin/v1/register`
- receive a single-use token required when registering

```json
// response
{
    "nonce": "<received_nonce>"
}
```

2. Build an HMAC using the received nonce and the server's shared secret (stored `homeserver.yaml`)
3. Send account registration request: `POST /_synapse/admin/v1/register`

```json
// request
{
    "nonce": "<received_nonce>",
    "username": "<username>",
    "displayname": "<display_name>",  // optional
    "password": "<password>",
    "admin": false,
    "mac": "<generated_hmac>"
}

// response
{
    "user_id": "@<username>:m",
    "access_token": "<received_token>",
    "home_server": "m",
    "device_id": "<generated_device_id>"
}
```

#### Login: `POST /_matrix/client/v3/login`
- login with the account created through the Synapse Admin API endpoint
- receive access token to use for following requests
- better to store and reuse the access token at next app start, instead of calling `/login` every time
- device ID of user should be provided only if it can reliably be distinguished from other devices of user
- if user login happens through the backend, there is the option to provide a device_id per browser profile (generated
random ID stored in localStorage or database when user registers for the first time)

```json
// request
{
    "type": "m.login.password",
    "identifier": {
        "type": "m.id.user",
        "user": "<username>"
    },
    "password": "<password>",
    "device_id": "<device_id>"    // optional (provide only if device can be distinguished)
}

// response
{
    "user_id": "@<username>:m",
    "access_token": "<received_token>",
    "device_id": "<device_id>",   // if device ID not given in request, new one generated
}
```

#### Logout: `POST /_matrix/client/v3/logout`
- log out the user from the device associated with the token given in Authorization header

### Rooms Management
#### Create Room: `POST /_matrix/client/v3/createRoom`
- creator of room is automatically added in room with admin power level
- default join behaviour dependent on preset and join rules: private preset requires invite unless overriding join rules
- _Element_ encrypts some rooms by default with E2EE $\rightarrow$ Synapse and backend cannot read messages
- to prevent encryption if needed, set in `homeserver.yaml`: `encryption_enabled_by_default_for_room_type: off`

```json
// request
{
    "creation_content": {
        "m.federate": false                 // local-only, hidden from other homeservers (no federation)
    },
    "name": "<room_name>",
    "room_alias_name": "<room_alias>",      // optional
    "topic": "<room_description>",          // optional
    "preset": "private_chat",               // controls default visibility/rules
    "initial_state": [                      // optional, needed if users are to self-join room without invitation
        {
            "type": "m.room.join_rules",
            "state_key": "",
            "content": {
                "join_rule": "restricted",  // any user member of the given space can join the room without invitation
                "allow": [
                    {
                        "type": "m.room_membership",
                        "room_id": "!<space>:m"
                    }
                ]
            }
        }
    ]
}

// response
{
    "room_id": "!<room_id>:m"
}
```

#### Create Space: `POST /_matrix/client/v3/createRoom`
- very similar with normal room

```json
// request
{
    "creation_content": {
        "m.federate": false             // local-only, hidden from other homeservers
    },
    "name": "<space_name>",
    "room_alias_name": "<space_alias>", // optional
    "topic": "<space_description>",     // optional
    "preset": "private_chat",
    "type": "m.space"
}

// response
{
    "room_id": "!<space_id>:m"
}
```

#### Room - Space Relationships
Add room to space: `PUT /_matrix/client/v3/rooms/{spaceId}/state/m.space.child/{roomId}`
- creates child -> parent relationship

```json
// request
{
    "via": ["m"],
    "suggested": false // if to highlight room in space
}
```

Add parent space to room: `PUT /_matrix/client/v3/rooms/{roomId}/state/m.space.parent/{spaceId}`
- creates parent -> child relationship

```json
// request
{
    "via": ["m"]
}
```

#### Room Aliases
Get room ID from alias: `GET /_matrix/client/v3/directory/room/{roomAlias}`

```json
// response
{
    "room_id": "!<room_id>:m",
    "servers": [ // list of servers aware of the room alias
        "m",
        ...
    ]
}
```

Add room alias: `PUT /_matrix/client/v3/directory/room/{roomAlias}`

```json
// request
{
    "room_id": "!<room_id>:m"
}
```

#### Room Details
Change room name: `PUT /_matrix/client/v3/rooms/{roomId}/state/m.room.name`

```json
// request
{
    "name": "<new_room_name>"
}
```

Change room topic: `PUT /_matrix/client/v3/rooms/{roomId}/state/m.room.topic`

```json
// request
{
    "topic": "<new_topic>"
}
```

Get room states: `GET /_matrix/client/v3/rooms/{roomId}/state`
- get one state `GET /_matrix/client/v3/rooms/{roomId}/state/{eventType}/{stateKey}` where (`eventType`, `stateKey`) =
(`m.room.name`, ` `), (`m.room.join_rules`, ` `), (`m.room.member`, `{userId}`) etc

Get list of joined rooms of user: `GET /_matrix/client/v3/joined_rooms`

```json
// response
{
    "joined_rooms": [
        "!<room_id>:m"
    ]
}
```

#### Room Membership
Invite user to room: `POST /_matrix/client/v3/rooms/{roomId}/invite`
- inviter needs to be member of the room

```json
// request
{
    "user_id": "@<username>:m"
}
```

Join room: `POST /_matrix/client/v3/rooms/{roomId}/join`
- user can join if invited OR can join without an invitation **if** they satisfy at least one condition of _restricted_
join (set up when creating room)

```json
// response
{
    "room_id": "!<room_id>:m"
}
```

Force-join room: `POST /_synapse/admin/v1/join/{roomId}`
- requires admin token for authentication
- automatically add user in room/space without needing to invite user

```json
// request
{
    "user_id": "@<username>:m"
}
```

Leave room: `POST /_matrix/client/v3/rooms/{roomId}/leave`

```json
// reponse
{
    "room_id": "!<room_id>:m"
}
```

### Messages Management
#### Send Message: `PUT /_matrix/client/v3/rooms/{roomId}/send/m.room.message/{txnId}`
- `txnID` = transaction ID $\rightarrow$ unique identifier of the request per device (used to not duplicate message
when request retried)

```json
// request
{
    "msgtype": "m.text", // or m.notice (for system messages or notifications)
    "body": "<message_string>"
}

// response
{
    "event_id": "$<event_id>:m"
}
```

#### Delete Message: `PUT /_matrix/client/v3/rooms/{roomId}/redact/{eventId}/{txnId}`
- `eventId` = ID of message stored in Synapse which is to be redacted
- message remains in timeline of room but content is removed

```json
// request
{
    "reason": "<reason_string>" // optional
}

// reponse
{
    "event_id": "$<redacted_event_id>:m"
}
```

#### Sync Events: `GET /_matrix/client/v3/sync`
- backend doesn't need to call this endpoint, it is used mainly by _Element_ to display current state to user
- _Element_ sends request to Synapse which holds connection open until there are new events and responds, then exchange
is repeated
- relevant query parameter: `?timeout=<value>` $\rightarrow$ Synapse sends empty response if no event in the specified
time period

## Possible Flows
- for creating a new user account and joining a room:

A. **Self-join user from space**
1. Create user account through Admin API
2. Ensure user is member of the space of the app (force-join user inside space)
3. Create rooms with _restricted_ join rule allowing membership via the space
4. Users can self-join rooms without invitations

B. **Force-join user per room**
1. Create user account through Admin API
2. Backend invites (user needs to accept the invitation) or force-joins user into each room
