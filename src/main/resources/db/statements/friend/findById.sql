SELECT
  sender,
  recipient,
  "isAccepted"
FROM public."Friend"
WHERE sender IN (?, ?) AND recipient IN (?, ?)