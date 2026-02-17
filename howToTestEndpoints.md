# Testing API Endpoints - With New Integrated Authentication

### 1. Register a New User

Create a user account by sending a POST request to the registration endpoint.

**Endpoint:** `POST /api/register`

**Request Body:**

```json
{
    "username": "your_username",
    "password": "your_password"
}
```

### 2. Login as Your User

Login as the user you just created, by sending a POST using the username and password.

**Endpoint:** `POST /api/login`

**Request Body**

```json
{
    "username": "your_username",
    "password": "your_password"
}
```

### 3. Get the Authorization Token

In the HTTP Response you will find a header called "Authorization", copy the string that comes after it.

**Example:**

```http
Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1MTIiLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3MTg0NjgzMSwiZXhwIjoxNzcxODUwNDMxfQ.G
gns6t3C7MXnKfU4S1U5LWKl7S8WH3EXRpfbsg_gX20
```

### 4. Use the Token to Test Endpoints

Make sure to include the authorization header with the token in every request after the first steps.

**Endpoint + Header**:

```http
GET /api/test
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1MTIiLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3MTg0NjgzMSwiZXhwIjoxNzcxODUwN
DMxfQ.Ggns6t3C7MXnKfU4S1U5LWKl7S8WH3EXRpfbsg_gX20
```

### Important Notes

- Always use the "Bearer" keyword in the Authorization
- Tokens expire after 1 hour, login again if you test for longer
