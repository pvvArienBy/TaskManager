CREATE DATABASE report_service;

\c report_service;

CREATE USER reportservice WITH PASSWORD 'reportservice';

CREATE SCHEMA IF NOT EXISTS report_app;
GRANT ALL ON SCHEMA report_app TO reportservice;

CREATE TABLE IF NOT EXISTS report_app.reports
(
    uuid        uuid                           NOT NULL,
    date_create timestamp(3) without time zone NOT NULL,
    date_update timestamp(3) without time zone NOT NULL,
    status      character varying(255),
    type        character varying(255),
    description character varying(500),
    CONSTRAINT reports_pkey PRIMARY KEY (uuid),
    CONSTRAINT reports_status_check CHECK (status::text = ANY
                                           (ARRAY ['LOADER'::character varying, 'PROGRESS'::character varying, 'ERROR'::character varying, 'DONE'::character varying]::text[])),
    CONSTRAINT reports_type_check CHECK (type::text = 'JOURNAL_AUDIT'::text)
);

ALTER TABLE IF EXISTS report_app.reports
    OWNER to reportservice;

CREATE TABLE IF NOT EXISTS report_app.param
(
    uuid      uuid NOT NULL,
    user_uuid uuid,
    from_date date,
    too_date  date,
    CONSTRAINT param_pkey PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS report_app.param
    OWNER to reportservice;

CREATE TABLE IF NOT EXISTS report_app.reports_param
(
    param_id   uuid,
    reports_id uuid NOT NULL,
    CONSTRAINT reports_param_pkey PRIMARY KEY (reports_id),
    CONSTRAINT param_id_uuid FOREIGN KEY (param_id)
        REFERENCES report_app.param (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT reports_id_uuid FOREIGN KEY (reports_id)
        REFERENCES report_app.reports (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS report_app.reports_param
    OWNER to reportservice;


CREATE TABLE IF NOT EXISTS report_app.files_info
(
    id          bigint                         NOT NULL,
    file_name   character varying(255),
    date_create timestamp(6) without time zone NOT NULL,
    CONSTRAINT files_info_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS report_app.files_info
    OWNER to reportservice;

CREATE TABLE IF NOT EXISTS report_app.report_file
(
    file_id    bigint NOT NULL,
    reports_id uuid,
    CONSTRAINT report_file_pkey PRIMARY KEY (file_id),
    CONSTRAINT file_id_to_id FOREIGN KEY (file_id)
        REFERENCES report_app.files_info (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT report_id_to_uuid FOREIGN KEY (reports_id)
        REFERENCES report_app.reports (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS report_app.report_file
    OWNER to reportservice;

CREATE SEQUENCE IF NOT EXISTS report_app.files_info_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE report_app.files_info_seq
    OWNER TO reportservice;

GRANT ALL PRIVILEGES ON DATABASE report_service TO reportservice;
