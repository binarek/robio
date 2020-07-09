CREATE TABLE person
(
    id bigserial PRIMARY KEY,
    external_id uuid UNIQUE NOT NULL,
    email varchar(50) UNIQUE NOT NULL,
    version bigint NOT NULL,
    notes varchar(1000),
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    role varchar(30) NOT NULL
);

CREATE UNIQUE INDEX person_external_id_role_idx ON person (external_id, role);

CREATE UNIQUE INDEX person_email_idx ON person (email);

CREATE INDEX person_version_idx ON person (version);

-- update team_member

ALTER TABLE team_member DROP COLUMN first_name;

ALTER TABLE team_member DROP COLUMN last_name;

DELETE FROM team_member;

ALTER TABLE team_member ADD COLUMN competitor_id uuid NOT NULL;

ALTER TABLE team_member ADD CONSTRAINT team_member_competitor_id_fkey
FOREIGN KEY (competitor_id) REFERENCES person (external_id);

CREATE INDEX team_member_competitor_id_idx ON team_member (competitor_id);
