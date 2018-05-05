--chats renaming
ALTER TABLE "Chat"
  RENAME TO chats;
ALTER TABLE chats
  RENAME CONSTRAINT "Chat_fk0" TO chats_events_fk;

--events renaming
ALTER TABLE "Event"
  RENAME TO events;
ALTER TABLE events
  RENAME creator TO creator_id;
ALTER TABLE events
  RENAME width TO latitude;
ALTER TABLE events
  RENAME eventplacename TO place_name;
ALTER TABLE events
  RENAME type TO type_id;
ALTER TABLE events
  RENAME folder TO folder_id;
ALTER TABLE events
  RENAME CONSTRAINT "Event_fk0" TO events_users_fk;
ALTER TABLE events
  RENAME CONSTRAINT "Event_fk2" TO events_event_types_fk;
ALTER TABLE events
  DROP CONSTRAINT "Event_fk3";

--folders renaming
ALTER TABLE "Folder"
  RENAME TO folders;
ALTER TABLE folders
  RENAME creator TO creator_id;
ALTER TABLE folders
  RENAME CONSTRAINT "Folder_fk0" TO folders_users_fk;

--friends renaming
ALTER TABLE "Friend"
  RENAME TO friends;
ALTER TABLE friends
  RENAME sender TO sender_id;
ALTER TABLE friends
  RENAME recipient TO recipient_id;
ALTER TABLE friends
  RENAME "isAccepted" TO is_accepted;
ALTER TABLE friends
  RENAME CONSTRAINT "Friend_fk0" TO sender_users_fk;
ALTER TABLE friends
  RENAME CONSTRAINT "Friend_fk1" TO recipient_users_fk;

--items renaming
ALTER TABLE "Item"
  RENAME TO items;
ALTER TABLE items
  RENAME person TO user_id;
ALTER TABLE items
  RENAME booker TO booker_id;
ALTER TABLE items
  RENAME priority TO priority_id;
ALTER TABLE items
  RENAME root TO root_id;
ALTER TABLE items
  RENAME CONSTRAINT "Item_fk0" TO items_users_fk;
ALTER TABLE items
  RENAME CONSTRAINT "Item_fk1" TO items_bookers_fk;
ALTER TABLE items
  RENAME CONSTRAINT "Item_fk2" TO items_priority_fk;
ALTER TABLE items
  RENAME CONSTRAINT "Item_fk3" TO items_root_fk;

--tags renaming
ALTER TABLE "ItemTag"
  DROP tag;
ALTER TABLE "Tag"
  RENAME TO tags;
ALTER TABLE tags
  DROP CONSTRAINT tag_pk;
ALTER TABLE tags
  ADD COLUMN tag_id SERIAL CONSTRAINT tag_pk PRIMARY KEY;

--item_tags renaming
ALTER TABLE "ItemTag"
  RENAME TO item_tags;
ALTER TABLE item_tags
  RENAME item TO item_id;
ALTER TABLE item_tags
  ADD tag_id INTEGER CONSTRAINT item_tags_tags_fk REFERENCES tags;
ALTER TABLE item_tags
  RENAME CONSTRAINT "ItemTag_fk0" TO item_tags_items_fk;

--messages renaming
ALTER TABLE "Message"
  RENAME TO messages;
ALTER TABLE messages
  RENAME sender TO sender_id;
ALTER TABLE messages
  RENAME chat TO chat_id;

--notes renaming;
ALTER TABLE "Note"
  RENAME TO notes;
ALTER TABLE notes
  RENAME creator TO creator_id;
ALTER TABLE notes
  RENAME folder TO folder_id;
ALTER TABLE notes
  RENAME CONSTRAINT "Note_fk0" TO notes_users_fk;
ALTER TABLE notes
  RENAME CONSTRAINT "Note_fk1" TO notes_folders_fk;

--participants renaming
ALTER TABLE "Participant"
  RENAME TO participants;
ALTER TABLE participants
  RENAME person TO user_id;
ALTER TABLE participants
  RENAME event TO event_id;
ALTER TABLE participants
  RENAME priority TO priority_id;
ALTER TABLE participants
  RENAME CONSTRAINT "Participant_fk0" TO participants_users_fk;
ALTER TABLE participants
  RENAME CONSTRAINT "Participant_fk1" TO participants_events_fk;
ALTER TABLE participants
  RENAME CONSTRAINT "Participant_fk2" TO participants_priorities_fk;

--users renaming
ALTER TABLE "Person"
  RENAME TO users;
ALTER TABLE users
  RENAME person_id TO user_id;
ALTER TABLE users
  RENAME birthday TO birthdate;

--user chats renaming
ALTER TABLE "PersonChat"
  RENAME TO user_chats;
ALTER TABLE user_chats
  RENAME person_id TO user_id;
ALTER TABLE user_chats
  RENAME last_read_message TO last_read_message_id;
ALTER TABLE user_chats
  RENAME CONSTRAINT "PersonChat_fk0" TO user_chats_users_fk;
ALTER TABLE user_chats
  RENAME CONSTRAINT "PersonChat_fk1" TO user_chats_chats_fk;
ALTER TABLE user_chats
  RENAME CONSTRAINT "PersonChat_fk2" TO user_chats_messages_fk;

--user folders renaming
ALTER TABLE "PersonFolder"
  RENAME TO user_folders;
ALTER TABLE user_folders
  RENAME person_id TO user_id;
ALTER TABLE user_folders
  RENAME CONSTRAINT "PersonFolder_fk0" TO user_folders_users_fk;
ALTER TABLE user_folders
  RENAME CONSTRAINT "PersonFolder_fk1" TO user_folders_folders_fk;

--priorities renaming
ALTER TABLE "Priority"
  RENAME TO priorities;
ALTER TABLE priorities
  RENAME value TO name;

--event types renaming
ALTER TABLE "Type"
  RENAME TO event_types;
ALTER TABLE event_types
  RENAME value TO name;

--verification token renaming
ALTER TABLE "Verif_token"
  RENAME TO verif_token;
ALTER TABLE verif_token
    RENAME birthday to birthdate;