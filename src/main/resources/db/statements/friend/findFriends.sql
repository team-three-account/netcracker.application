SELECT DISTINCT
  person_id,
  name,
  surname,
  email,
  password,
  role,
  phone,
  birthday
FROM public."Person"
  INNER JOIN "Friend"
    ON "Person".person_id = "Friend".sender OR "Person".person_id = "Friend".recipient
WHERE (sender = ? OR recipient = ?) AND "isAccepted" = TRUE AND person_id <> ?



-- SELECT
--   person_id,
--   name,
--   surname,
--   email,
--   password,
--   role,
--   phone,
--   birthday
-- FROM public."Person"
-- WHERE person_id = (SELECT DISTINCT sender
--                    FROM public."Friend"
--                    WHERE recipient = ? AND "isAccepted" = TRUE)
--       OR person_id = (SELECT DISTINCT recipient
--                       FROM public."Friend"
--                       WHERE sender = ? AND "isAccepted" = TRUE)