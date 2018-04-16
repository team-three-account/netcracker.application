create table verif_token
(
  token_id varchar not null
    constraint verif_token_pkey
    primary key,
  user_id  varchar not null,
  name     text    not null,
  surname  text    not null,
  email    text    not null,
  password text    not null,
  role     text    not null,
  phone    text    not null,
  birthday date    not null
);

create table person
(
  person_id text not null
    constraint person_pkey
    primary key,
  name      text not null,
  surname   text not null,
  email     text not null,
  password  text not null,
  role      text not null,
  phone     text,
  birthday  date not null
);


