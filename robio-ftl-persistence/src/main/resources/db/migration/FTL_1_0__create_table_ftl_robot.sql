CREATE TABLE ftl.robot
(
    id bigserial PRIMARY KEY,
    robot_id uuid NOT NULL,
    competition_id uuid NOT NULL,
    name text NOT NULL,
    qualification text NOT NULL,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX robot_robot_id_competition_id_idx ON ftl.robot(robot_id, competition_id);
