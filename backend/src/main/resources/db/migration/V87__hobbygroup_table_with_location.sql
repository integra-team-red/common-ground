ALTER TABLE hobby_group ADD COLUMN group_location_id UUID,
ADD CONSTRAINT fk_hobby_group_location FOREIGN KEY (group_location_id) REFERENCES location (id);
