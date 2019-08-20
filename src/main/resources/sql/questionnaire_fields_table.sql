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