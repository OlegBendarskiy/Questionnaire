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

DROP TABLE IF EXISTS questionnaire_field;

CREATE TABLE questionnaire_field
(
    id bigint NOT NULL,
    label character varying(30) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    type character varying(30)
        CHECK (type IN ('single line text', 'multiline text', 'radio button', 'checkbox', 'combobox', 'date'))
            COLLATE pg_catalog."default" NOT NULL,
    options character varying COLLATE pg_catalog."default",
    required boolean default false,
    is_active boolean default false,
    CONSTRAINT "questionnaire_field_pkey" PRIMARY KEY (id)
);

INSERT INTO public.questionnaire_field(
	id, label, type, options, required, is_active)
	VALUES
	(1, 'Full Name', 'single line text', '', true, true),
	(2, 'Email', 'single line text', '', true, true),
	(3, 'Sex', 'radio button', 'Male, Female', false, true);

SELECT * FROM questionnaire_field;

DROP TABLE IF EXISTS q_user;

CREATE TABLE q_user
(
    id bigint NOT NULL,
    first_name character varying(25) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(25) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(13) COLLATE pg_catalog."default" NOT NULL,
    email character varying(30) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    password character varying(16) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "User_pkey" PRIMARY KEY (id)
);

INSERT INTO public.q_user(
	id, first_name, last_name, phone_number, email, password)
	VALUES
	(1, 'Aaa', 'Aaa', '+380000000000', 'aaa@mail.com', 'aaaaaa'),
	(2, 'Qqq', 'Qqq', '+380999999999', 'qqq@mail.com', 'qqqqqq'),
	(3, 'Zzz', 'Zzz', '+380888888888', 'zzz@mail.com', 'zzzzzz');

SELECT * FROM q_user;