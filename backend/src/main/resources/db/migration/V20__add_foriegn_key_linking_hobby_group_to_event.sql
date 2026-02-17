alter table Event
    ADD column hobby_group_id UUID constraint event_hobby_group_constraint References hobby_group(id)
