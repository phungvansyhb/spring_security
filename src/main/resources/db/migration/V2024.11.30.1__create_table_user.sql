CREATE TABLE public."user" (
	id bigint NOT NULL,
	"name" varchar NOT NULL,
	email varchar NULL,
	phone varchar NULL,
	avatar varchar NULL,
	created_date date NULL,
	updated_date date NULL,
	deteted_date date NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT user_unique UNIQUE ("name")
);
