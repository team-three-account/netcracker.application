ALTER TABLE users
  ADD COLUMN notification_start_date TIMESTAMP;

ALTER TABLE users
  ADD COLUMN notification_end_date TIMESTAMP;

ALTER TABLE users
  RENAME COLUMN notification TO notification_periodicity;