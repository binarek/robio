CREATE TABLE human_user
(
    id bigserial PRIMARY KEY,
    user_id uuid NOT NULL,
    password text NOT NULL,
    email varchar(50) NOT NULL,
    role text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    competitor_id uuid,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX human_user_user_id_idx ON human_user(user_id);

CREATE UNIQUE INDEX human_user_email_idx ON human_user(email);
