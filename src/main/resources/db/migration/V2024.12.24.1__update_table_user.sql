CREATE SEQUENCE user_id_seq;

ALTER TABLE public."user" ALTER COLUMN  :balance type bigint SET DEFAULT 0;