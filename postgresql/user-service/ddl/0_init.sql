CREATE DATABASE user_service;

\c user_service;

CREATE USER userservice WITH PASSWORD 'userservice';

CREATE SCHEMA IF NOT EXISTS user_app;
GRANT ALL ON SCHEMA user_app TO userservice;

CREATE TABLE IF NOT EXISTS user_app.users
(
    date_create timestamp(3) without time zone NOT NULL,
    date_update timestamp(3) without time zone NOT NULL,
    uuid        uuid                           NOT NULL,
    fio         character varying(255)         NOT NULL,
    mail        character varying(255)         NOT NULL,
    password    text                           NOT NULL,
    role        character varying(50)          NOT NULL,
    status      character varying(50)          NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (uuid),
    CONSTRAINT email UNIQUE (mail),
    CONSTRAINT users_role_check CHECK (role::text = ANY
                                       (ARRAY ['ADMIN'::character varying::text, 'USER'::character varying::text])),
    CONSTRAINT users_status_check CHECK (status::text = ANY
                                         (ARRAY ['WAITING_ACTIVATION'::character varying::text, 'ACTIVATED'::character varying::text, 'DEACTIVATED'::character varying::text]))
);

ALTER TABLE IF EXISTS user_app.users
    OWNER TO userservice;
GRANT ALL PRIVILEGES ON DATABASE user_service TO userservice;