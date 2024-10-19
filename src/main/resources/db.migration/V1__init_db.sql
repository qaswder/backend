CREATE SEQUENCE seq_author
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647;

CREATE TABLE lib_author
(
    id         int8 NOT NULL DEFAULT nextval('seq_author'),
    surname    VARCHAR(255),
    name       VARCHAR(255),
    patronymic VARCHAR(255),
    country    VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);


CREATE SEQUENCE seq_book
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647;

CREATE TABLE lib_book
(
    id         int8 NOT NULL DEFAULT nextval('seq_book'),
    title      VARCHAR(255),
    genre      VARCHAR(255),
    publisher  VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);

CREATE TABLE lib_author_book
(
    id_author int8 NOT NULL,
    id_book   int8 NOT NULL,
    PRIMARY KEY (id_author, id_book)
);
