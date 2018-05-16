INSERT INTO event_types("type_id", "name") VALUES (0, 'indefinite');
ALTER TABLE events ALTER COLUMN type_id DROP NOT NULL;