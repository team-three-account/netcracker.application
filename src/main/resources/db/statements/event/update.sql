UPDATE public."Event"
SET name         = ?,
  description    = ?,
  start_date     = ?,
  end_date       = ?,
  type           = ?,
  is_draft       = ?,
  width          = ?,
  longitude      = ?,
  eventplacename = ?
WHERE event_id = ?