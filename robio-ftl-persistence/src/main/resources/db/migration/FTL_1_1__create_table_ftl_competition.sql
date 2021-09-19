CREATE TABLE ftl.competition
(
    id bigserial PRIMARY KEY,
    competition_id uuid UNIQUE NOT NULL,
    version bigint NOT NULL,
    rules jsonb NOT NULL,
    state text NOT NULL,
    initialize_date_time timestamp without time zone NOT NULL,
    start_date_time timestamp without time zone,
    finish_date_time timestamp without time zone
);

CREATE UNIQUE INDEX competition_competition_id_idx ON ftl.competition(competition_id);
