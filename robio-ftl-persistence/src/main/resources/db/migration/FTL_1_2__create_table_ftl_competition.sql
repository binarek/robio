CREATE TABLE ftl.competition
(
    id bigserial PRIMARY KEY,
    competition_id uuid UNIQUE NOT NULL,
    version bigint NOT NULL,
    rules jsonb,
    state text NOT NULL,
    start_date_time timestamp with time zone NOT NULL,
    finish_date_time timestamp with time zone
);

CREATE TABLE ftl.competition_robot
(
    competition_id bigint NOT NULL,
    robot_id bigint NOT NULL,

    PRIMARY KEY(competition_id, robot_id),
    CONSTRAINT fk_competition_robot_robot_id FOREIGN KEY(robot_id) REFERENCES ftl.robot(id),
    CONSTRAINT fk_competition_robot_competition_id FOREIGN KEY(competition_id) REFERENCES ftl.competition(id)
);
