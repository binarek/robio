CREATE TABLE ftl.run
(
    id bigserial PRIMARY KEY,
    competition_id uuid NOT NULL,
    robot_id uuid NOT NULL,
    number int NOT NULL,
    judgement text NOT NULL,
    time bigint NOT NULL,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX run_competition_id_robot_id_number_idx ON ftl.run(competition_id, robot_id, number);
