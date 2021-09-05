CREATE TABLE ftl.robot
(
    id bigserial PRIMARY KEY,
    robot_id uuid UNIQUE NOT NULL,
    name text NOT NULL,
    version bigint NOT NULL
);

