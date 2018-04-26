SELECT
  token_id,
  user_id,
  name,
  surname,
  email,
  password,
  role,
  birthday,
  phone
FROM public."Verif_token"
WHERE token_id = ?