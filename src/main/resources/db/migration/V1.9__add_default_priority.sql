INSERT INTO priorities("priority_id", "name") VALUES (0, 'without');
ALTER TABLE items ALTER COLUMN priority_id SET DEFAULT 0;
ALTER TABLE participants ALTER COLUMN priority_id SET DEFAULT 0;