SELECT
  item_id,
  person,
  booker,
  name,
  link,
  due_date,
  priority,
  root
FROM public."Item"
WHERE public."Item".person = ?