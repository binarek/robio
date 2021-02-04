CREATE TABLE ftl.competition_plan
(
    id bigserial PRIMARY KEY,
    competition_id uuid UNIQUE NOT NULL,
    version bigint NOT NULL,
    rules jsonb
);

CREATE TABLE ftl.competition_plan_robot
(
    competition_plan_id bigint PRIMARY KEY,
    robot_id uuid NOT NULL,

    CONSTRAINT fk_competition_plan_id FOREIGN KEY(competition_plan_id) REFERENCES FTL.competition_plan(id)
);
