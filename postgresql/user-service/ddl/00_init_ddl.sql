CREATE DATABASE user_service;

\c user_service;

CREATE USER userservice WITH PASSWORD 'userservice';

CREATE SCHEMA IF NOT EXISTS user_app;
GRANT ALL ON SCHEMA user_app TO userservice;

CREATE TABLE IF NOT EXISTS user_app.users
(
    uuid        uuid                           NOT NULL,
    date_create timestamp(3) without time zone NOT NULL,
    date_update timestamp(3) without time zone NOT NULL,
    fio         character varying(255)         NOT NULL,
    mail        character varying(255)         NOT NULL,
    role        character varying(50)          NOT NULL,
    status      character varying(50)          NOT NULL,
    password    text                           NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (uuid),
    CONSTRAINT email UNIQUE (mail),
    CONSTRAINT users_role_check CHECK (role::text = ANY
                                       (ARRAY ['ADMIN'::character varying, 'USER'::character varying, 'MANAGER'::character varying]::text[])),
    CONSTRAINT users_status_check CHECK (status::text = ANY
                                         (ARRAY ['WAITING_ACTIVATION'::character varying, 'ACTIVATED'::character varying, 'DEACTIVATED'::character varying]::text[]))
);

ALTER TABLE IF EXISTS user_app.users
    OWNER TO userservice;
GRANT ALL PRIVILEGES ON DATABASE user_service TO userservice;

CREATE TABLE IF NOT EXISTS user_app.confirmation_token
(
    confirmed_at timestamp(3) without time zone,
    created_at   timestamp(3) without time zone NOT NULL,
    expires_at   timestamp(3) without time zone NOT NULL,
    id           bigint                         NOT NULL,
    token        uuid                           NOT NULL,
    user_uuid    uuid                           NOT NULL,
    CONSTRAINT confirmation_token_pkey PRIMARY KEY (id),
    CONSTRAINT useruuid_uuid FOREIGN KEY (user_uuid)
        REFERENCES user_app.users (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS user_app.confirmation_token_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

GRANT ALL ON SEQUENCE user_app.confirmation_token_sequence TO userservice;

ALTER TABLE IF EXISTS user_app.confirmation_token
    OWNER TO userservice;
