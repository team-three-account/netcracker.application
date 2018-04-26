SELECT
  event_id,
  "Event".name,
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
  INNER JOIN "Type" ON "Type".type_id = "Event".type
  INNER JOIN "Person" ON "Person".person_id = "Event".creator