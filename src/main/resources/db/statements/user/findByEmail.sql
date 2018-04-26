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
WHERE email = ?