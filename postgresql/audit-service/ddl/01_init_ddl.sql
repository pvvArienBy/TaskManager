CREATE DATABASE audit_service;

\c audit_service;

CREATE USER auditservice WITH PASSWORD 'auditservice';

CREATE SCHEMA IF NOT EXISTS audit_app;
GRANT ALL ON SCHEMA audit_app TO auditservice;

CREATE TABLE IF NOT EXISTS audit_app.audits
(
    uuid        uuid NOT NULL,
    date_create timestamp(3) without time zone,
    user_uuid   uuid,
    user_fio    character varying(255),
    user_mail   character varying(255),
    user_role   character varying(255),
    text        character varying(255),
    type        character varying(255),
    id          character varying(255),
    CONSTRAINT audits_pkey PRIMARY KEY (uuid),
    CONSTRAINT audits_type_check CHECK (type::text = ANY
                                        (ARRAY ['ADMIN'::character varying, 'COMMENT'::character varying, 'TASK'::character varying, 'USER'::character varying, 'PROJECT'::character varying]::text[])),
    CONSTRAINT audits_user_role_check CHECK (user_role::text = ANY
                                             (ARRAY ['ADMIN'::character varying, 'USER'::character varying, 'MANAGER'::character varying]::text[]))
);

ALTER TABLE IF EXISTS audit_app.audits
    OWNER TO auditservice;
GRANT ALL PRIVILEGES ON DATABASE audit_service TO auditservice;
