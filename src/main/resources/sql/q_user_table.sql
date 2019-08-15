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