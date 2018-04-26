SELECT
  event_id,
  name,
  description,
  creator,
  start_date,
  end_date,
  type,
  is_draft,
  folder,
  width,
  longitude,
  eventplacename,
  periodicity
FROM public."Event"
WHERE event_id =?