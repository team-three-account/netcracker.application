DELETE FROM public."Friend"
WHERE (recipient =? AND sender =?) OR (recipient =? AND sender =?)