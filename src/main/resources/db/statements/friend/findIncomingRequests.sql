SELECT
  person_id,
  name,
  surname,
  email,
  password,
  role,
  phone,
  birthday
FROM public."Person"
  INNER JOIN public."Friend" ON "Person".person_id = "Friend".sender
WHERE recipient = ? AND "isAccepted" = FALSE


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
-- WHERE person_id = (SELECT sender
--                    FROM public."Friend"
--                    WHERE recipient = ? AND "isAccepted" = FALSE)