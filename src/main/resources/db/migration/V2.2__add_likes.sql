CREATE TABLE likes
(
  item_id integer NOT NULL,
  user_id integer NOT NULL,
  is_liked boolean NOT NULL,
  CONSTRAINT likes_items_fk FOREIGN KEY (item_id)
  REFERENCES items (item_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT likes_users_fk FOREIGN KEY (user_id)
  REFERENCES users (user_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)