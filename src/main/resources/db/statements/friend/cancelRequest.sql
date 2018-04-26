DELETE FROM public."Friend"
WHERE sender = ? AND recipient = ? AND "isAccepted" = FALSE