DROP TABLE IF EXISTS questionnaire_response;

CREATE TABLE questionnaire_response
(
    id bigint NOT NULL,
    file_name character varying NOT NULL UNIQUE,
    CONSTRAINT "questionnaire_response_pkey" PRIMARY KEY (id)
);

SELECT * FROM questionnaire_response;