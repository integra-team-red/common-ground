CREATE TABLE Group_Tags (
    hobby_group_id UUID NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT fk_group FOREIGN KEY (hobby_group_id) REFERENCES hobby_group(id),
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES Tag(id),
    PRIMARY KEY (hobby_group_id, tag_id)
);

CREATE TABLE User_Tags (
    user_id UUID NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES Tag(id),
    PRIMARY KEY (user_id, tag_id)
);
