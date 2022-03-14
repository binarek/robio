CREATE TABLE auth_user
(
    id bigserial PRIMARY KEY,
    username varchar(60) NOT NULL,
    password text NOT NULL,
    role text NOT NULL,
    email text,
    first_name text,
    last_name text,
    competitor_id uuid,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX auth_user_username_idx ON auth_user(username);
