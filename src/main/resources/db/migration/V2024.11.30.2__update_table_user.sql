ALTER TABLE public."user" RENAME COLUMN deteted_date TO deleted_date;
ALTER TABLE public."user" ADD "password" varchar NULL;
