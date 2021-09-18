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
