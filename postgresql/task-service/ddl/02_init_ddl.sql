CREATE DATABASE task_service;

\c task_service;

CREATE USER taskservice WITH PASSWORD 'taskservice';

CREATE SCHEMA IF NOT EXISTS task_app;
GRANT ALL ON SCHEMA task_app TO taskservice;


CREATE TABLE IF NOT EXISTS task_app.projects
(
    uuid        uuid                    NOT NULL,
    date_create timestamp(3) without time zone,
    date_update timestamp(3) without time zone,
    manager     uuid,
    name        character varying(255)  NOT NULL,
    description character varying(1000) NOT NULL,
    status      character varying(255)  NOT NULL,
    CONSTRAINT projects_pkey PRIMARY KEY (uuid),
    CONSTRAINT projects_status_check CHECK (status::text = ANY
                                            (ARRAY ['ACTIVE'::character varying, 'ARCHIVE'::character varying]::text[]))
);

ALTER TABLE IF EXISTS task_app.projects
    OWNER TO taskservice;
GRANT ALL PRIVILEGES ON DATABASE task_service TO taskservice;


CREATE TABLE IF NOT EXISTS task_app.project_staff
(
    project_id uuid NOT NULL,
    staff      uuid,
    CONSTRAINT projectid_uuid FOREIGN KEY (project_id)
        REFERENCES task_app.projects (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS task_app.project_staff
    OWNER TO taskservice;
GRANT ALL PRIVILEGES ON DATABASE task_service TO taskservice;

CREATE TABLE IF NOT EXISTS task_app.tasks
(
    uuid        uuid                    NOT NULL,
    date_create timestamp(3) without time zone,
    date_update timestamp(3) without time zone,
    project     uuid,
    description character varying(1000) NOT NULL,
    status      character varying(255)  NOT NULL,
    title       character varying(255)  NOT NULL,
    implementer uuid,
    CONSTRAINT tasks_pkey PRIMARY KEY (uuid),
    CONSTRAINT tasks_status_check CHECK (status::text = ANY
                                         (ARRAY ['WAIT'::character varying, 'BLOCK'::character varying, 'IN_WORK'::character varying, 'DONE'::character varying, 'CLOSE'::character varying]::text[]))
);

ALTER TABLE IF EXISTS task_app.tasks
    OWNER TO taskservice;
GRANT ALL PRIVILEGES ON DATABASE task_service TO taskservice;