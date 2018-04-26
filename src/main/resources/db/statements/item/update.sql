UPDATE public."Item"
SET person_id = ?,
  booker_name = ?,
  item_name   = ?,
  link        = ?,
  due_date    = ?,
  priority    = ?,
  root        = ?
WHERE "Item".item_id = ?