CREATE TABLE robot
(
    id bigserial PRIMARY KEY,
    competition_id uuid NOT NULL,
    robot_id uuid NOT NULL,
    name text NOT NULL,
    qualification text NOT NULL,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX robot_competition_id_robot_id_idx ON robot(competition_id, robot_id);
