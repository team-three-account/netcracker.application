ALTER TABLE public.chats DROP CONSTRAINT chats_events_fk;
ALTER TABLE public.chats
ADD CONSTRAINT chats_events_fk
FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE;