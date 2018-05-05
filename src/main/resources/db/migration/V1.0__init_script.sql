CREATE TABLE "Priority"
(
  priority_id SERIAL      NOT NULL
    CONSTRAINT priority_pk
    PRIMARY KEY,
  value       VARCHAR(20) NOT NULL
);

CREATE TABLE "Person"
(
  person_id    SERIAL      NOT NULL
    CONSTRAINT person_pk
    PRIMARY KEY,
  name         VARCHAR(30) NOT NULL,
  surname      VARCHAR(50) NOT NULL,
  email        VARCHAR(50) NOT NULL,
  password     TEXT        NOT NULL,
  birthday     DATE,
  phone        VARCHAR(20),
  photo        TEXT,
  notification TEXT,
  role         TEXT        NOT NULL
);

CREATE TABLE "Friend"
(
  sender       INTEGER NOT NULL
    CONSTRAINT "Friend_fk0"
    REFERENCES "Person",
  recipient    INTEGER NOT NULL
    CONSTRAINT "Friend_fk1"
    REFERENCES "Person",
  "isAccepted" BOOLEAN NOT NULL
);

CREATE TABLE "Type"
(
  type_id SERIAL      NOT NULL
    CONSTRAINT type_pk
    PRIMARY KEY,
  value   VARCHAR(20) NOT NULL
);

CREATE TABLE "Item"
(
  item_id     SERIAL  NOT NULL
    CONSTRAINT item_pk
    PRIMARY KEY,
  person      INTEGER NOT NULL
    CONSTRAINT "Item_fk0"
    REFERENCES "Person",
  booker      INTEGER
    CONSTRAINT "Item_fk1"
    REFERENCES "Person",
  name        TEXT    NOT NULL,
  description TEXT    NOT NULL,
  image       TEXT,
  link        TEXT,
  due_date    DATE,
  priority    INTEGER
    CONSTRAINT "Item_fk2"
    REFERENCES "Priority",
  root        INTEGER
    CONSTRAINT "Item_fk3"
    REFERENCES "Item"
);

CREATE TABLE "Tag"
(
  name TEXT NOT NULL
    CONSTRAINT tag_pk
    PRIMARY KEY
);

CREATE TABLE "ItemTag"
(
  item INTEGER NOT NULL
    CONSTRAINT "ItemTag_fk0"
    REFERENCES "Item",
  tag  TEXT    NOT NULL
    CONSTRAINT "ItemTag_fk1"
    REFERENCES "Tag"
);

CREATE TABLE "Folder"
(
  folder_id SERIAL
    CONSTRAINT folder_pk
    PRIMARY KEY,
  name      TEXT    NOT NULL,
  creator   INTEGER NOT NULL
    CONSTRAINT "Folder_fk0"
    REFERENCES "Person"
);

CREATE TABLE "Event"
(
  event_id       SERIAL  NOT NULL
    CONSTRAINT event_pk
    PRIMARY KEY,
  name           TEXT    NOT NULL,
  description    TEXT    NOT NULL,
  creator        INTEGER NOT NULL
    CONSTRAINT "Event_fk0"
    REFERENCES "Person",
  start_date     DATE,
  end_date       DATE,
  width          DOUBLE PRECISION,
  longitude      DOUBLE PRECISION,
  eventplacename TEXT,
  periodicity    TEXT,
  type           BIGINT  NOT NULL
    CONSTRAINT "Event_fk2"
    REFERENCES "Type",
  is_draft       BOOLEAN,
  folder         INTEGER
    CONSTRAINT "Event_fk3"
    REFERENCES "Folder",
  photo          TEXT
);

CREATE TABLE "Participant"
(
  person      INTEGER NOT NULL
    CONSTRAINT "Participant_fk0"
    REFERENCES "Person"
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  event       INTEGER NOT NULL
    CONSTRAINT "Participant_fk1"
    REFERENCES "Event"
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  priority    INTEGER
    CONSTRAINT "Participant_fk2"
    REFERENCES "Priority",
  countdown   BOOLEAN,
  is_accepted BOOLEAN NOT NULL
);

CREATE TABLE "PersonFolder"
(
  person_id INTEGER NOT NULL
    CONSTRAINT "PersonFolder_fk0"
    REFERENCES "Person",
  folder_id INTEGER NOT NULL
    CONSTRAINT "PersonFolder_fk1"
    REFERENCES "Folder"
);

CREATE TABLE "Chat"
(
  chat_id  SERIAL  NOT NULL
    CONSTRAINT chat_pk
    PRIMARY KEY,
  name     TEXT    NOT NULL,
  event_id INTEGER NOT NULL
    CONSTRAINT "Chat_fk0"
    REFERENCES "Event"
);

CREATE TABLE "Message"
(
  message_id SERIAL  NOT NULL
    CONSTRAINT message_pk
    PRIMARY KEY,
  text       TEXT    NOT NULL,
  date       DATE    NOT NULL,
  sender     INTEGER NOT NULL,
  chat       INTEGER NOT NULL
);

CREATE TABLE "PersonChat"
(
  person_id         INTEGER NOT NULL
    CONSTRAINT "PersonChat_fk0"
    REFERENCES "Person",
  chat_id           INTEGER NOT NULL
    CONSTRAINT "PersonChat_fk1"
    REFERENCES "Chat",
  last_read_message INTEGER NOT NULL
    CONSTRAINT "PersonChat_fk2"
    REFERENCES "Message"
);

CREATE TABLE "Verif_token"
(
  token_id TEXT   NOT NULL
    CONSTRAINT verif_token_pk
    PRIMARY KEY,
  user_id  SERIAL NOT NULL,
  name     TEXT   NOT NULL,
  surname  TEXT   NOT NULL,
  email    TEXT   NOT NULL,
  password TEXT   NOT NULL,
  role     TEXT   NOT NULL,
  birthday DATE,
  phone    TEXT
);

CREATE TABLE "Note"
(
  note_id     SERIAL  NOT NULL
    CONSTRAINT note_pk
    PRIMARY KEY,
  name        TEXT    NOT NULL,
  description TEXT    NOT NULL,
  creator     INTEGER NOT NULL
    CONSTRAINT "Note_fk0"
    REFERENCES "Person",
  folder      INTEGER
    CONSTRAINT "Note_fk1"
    REFERENCES "Folder"
);

INSERT INTO "Type" (value)
VALUES ('private');
INSERT INTO "Type" (value)
VALUES ('public');
INSERT INTO "Type" (value)
VALUES ('only_for_friends');

INSERT INTO "Priority" (value)
VALUES ('urgent');
INSERT INTO "Priority" (value)
VALUES ('normal');
INSERT INTO "Priority" (value)
VALUES ('low');