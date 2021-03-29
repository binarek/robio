CREATE TABLE ftl.competition_plan
(
    id bigserial PRIMARY KEY,
    competition_id uuid UNIQUE NOT NULL,
    version bigint NOT NULL,
    rules jsonb
);

CREATE TABLE ftl.competition_plan_robot
(
    competition_plan_id bigint NOT NULL,
    robot_id uuid NOT NULL,

    PRIMARY KEY(competition_plan_id, robot_id),
    CONSTRAINT fk_competition_plan_id FOREIGN KEY(competition_plan_id) REFERENCES FTL.competition_plan(id)
);
