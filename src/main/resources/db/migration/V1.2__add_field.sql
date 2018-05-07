ALTER TABLE items
  ADD event_id integer;
ALTER TABLE items
  ADD CONSTRAINT "item_event_fk"
FOREIGN KEY (event_id) REFERENCES events(event_id);