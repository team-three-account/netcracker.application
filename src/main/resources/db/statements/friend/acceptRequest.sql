UPDATE public."Friend"
SET "isAccepted" = TRUE
WHERE recipient = ? AND sender = ? AND "isAccepted" = FALSE