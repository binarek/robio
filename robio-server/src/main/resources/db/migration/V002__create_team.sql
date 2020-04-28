CREATE TABLE team
(
    id bigserial PRIMARY KEY,
    external_id uuid UNIQUE NOT NULL,
    version bigint NOT NULL,
    name varchar(100) UNIQUE NOT NULL,
    notes varchar(1000)
);

CREATE TABLE team_member
(
    id bigserial PRIMARY KEY,
    team_id bigint NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    role varchar(30) NOT NULL,
    CONSTRAINT team_member_team_id_fkey FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE UNIQUE INDEX team_external_id_idx ON team (external_id);

CREATE UNIQUE INDEX team_name_idx ON team (name);

CREATE INDEX team_version_idx ON robot (version);

CREATE INDEX team_member_team_id_idx ON team_member (team_id);

-- update robot

ALTER TABLE robot ADD COLUMN team_id uuid NOT NULL;

ALTER TABLE robot ADD CONSTRAINT robot_team_id_fkey FOREIGN KEY (team_id) REFERENCES team (external_id);

CREATE INDEX robot_team_id_idx ON robot (team_id);
