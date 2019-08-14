DROP TABLE IF EXISTS questionnaire_response;

CREATE TABLE questionnaire_response
(
    id bigint NOT NULL,
    file_name character varying NOT NULL UNIQUE,
    CONSTRAINT "questionnaire_response_pkey" PRIMARY KEY (id)
);

INSERT INTO public.questionnaire_response(id, file_name)
	VALUES
	(1, 'response_1'),
	(2, 'response_2');
SELECT * FROM questionnaire_response;