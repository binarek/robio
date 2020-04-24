CREATE TABLE robot
(
    id bigserial PRIMARY KEY,
    external_id uuid UNIQUE NOT NULL,
    name varchar(100) UNIQUE NOT NULL,
    notes varchar(1000),
    weight numeric,
    width numeric,
    length numeric,
    height numeric
);

CREATE UNIQUE INDEX robot_external_id_idx ON robot (external_id);

CREATE UNIQUE INDEX robot_name_idx ON robot (name);
