ALTER TABLE public.chats DROP CONSTRAINT chats_events_fk;
ALTER TABLE public.chats
ADD CONSTRAINT chats_events_fk
FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE;
ALTER TABLE public.messages DROP CONSTRAINT messages_chats_fk;
ALTER TABLE public.messages
ADD CONSTRAINT messages_chats_fk
FOREIGN KEY (chat_id) REFERENCES chats (chat_id) ON DELETE CASCADE;