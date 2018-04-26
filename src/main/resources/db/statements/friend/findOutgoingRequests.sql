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
  INNER JOIN public."Friend" ON "Person".person_id = "Friend".recipient
WHERE sender = ? AND "isAccepted" = FALSE


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
-- WHERE person_id = (SELECT recipient
--                    FROM public."Friend"
--                    WHERE sender = ? AND "isAccepted" = FALSE)