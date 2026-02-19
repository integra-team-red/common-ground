ALTER TABLE Event
    drop column hobby_group_id,
    add column hobby_group_id uuid,
    ADD CONSTRAINT fk_l_id
        FOREIGN KEY (hobby_group_id)
            REFERENCES hobby_group (id);
