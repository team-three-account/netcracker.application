ALTER TABLE public.messages DROP CONSTRAINT messages_chats_fk;
ALTER TABLE public.messages
ADD CONSTRAINT messages_chats_fk
FOREIGN KEY (chat_id) REFERENCES chats (chat_id) ON DELETE CASCADE;