ALTER TABLE location ADD COLUMN creator_id UUID,
ADD CONSTRAINT fk_location_creator FOREIGN KEY (creator_id) REFERENCES users (id);
