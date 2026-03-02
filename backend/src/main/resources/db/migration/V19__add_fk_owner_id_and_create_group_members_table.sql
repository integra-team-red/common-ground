ALTER TABLE hobby_group
    ADD COLUMN owner_id uuid,
    ADD CONSTRAINT fk_owner_hobby_group FOREIGN KEY (owner_id) references users(id);

CREATE TABLE group_members
(
    user_id uuid REFERENCES users(id),
    hobby_group_id uuid REFERENCES hobby_group(id),
    primary key (user_id, hobby_group_id)
)
