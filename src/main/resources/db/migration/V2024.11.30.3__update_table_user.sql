ALTER TABLE public."user" ADD "role" varchar DEFAULT 'USER' NOT NULL;
ALTER TABLE public."user" ALTER COLUMN created_date TYPE timestamp USING created_date::timestamp;
ALTER TABLE public."user" ALTER COLUMN created_date SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE public."user" ALTER COLUMN updated_date TYPE timestamp USING updated_date::timestamp;
ALTER TABLE public."user" ALTER COLUMN updated_date SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE public."user" ALTER COLUMN deleted_date TYPE timestamp USING deleted_date::timestamp;
