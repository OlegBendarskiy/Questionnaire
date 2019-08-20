DROP TABLE IF EXISTS response;

CREATE TABLE response
(
    id bigserial NOT NULL,
    "Full Name" character varying(30) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N/A',
    "Email" character varying(30) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N/A',
    "Sex" character varying CHECK ("Sex" IN ('Male', 'Female', 'N/A'))
                      COLLATE pg_catalog."default" NOT NULL DEFAULT 'N/A',
    CONSTRAINT "response_pkey" PRIMARY KEY (id)
);

INSERT INTO public.response(
	"Full Name", "Email", "Sex")
	VALUES
	('Ivan Ivanov', 'ivanov@mail.com', 'Male'),
	('Ivanna Ivanova', 'ivanova@mail.com', 'Female');
	
INSERT INTO public.response(
	"Full Name", "Email")
	VALUES
	('Petr', 'Petr@mail.com');
	
SELECT * FROM response;