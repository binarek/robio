CREATE TABLE auth_user
(
    id bigserial PRIMARY KEY,
    user_id uuid NOT NULL,
    username varchar(60) NOT NULL,
    password text NOT NULL,
    role text NOT NULL,
    email text,
    first_name text,
    last_name text,
    competitor_id uuid,
    version bigint NOT NULL
);

CREATE UNIQUE INDEX auth_user_user_id_idx ON auth_user(user_id);

CREATE UNIQUE INDEX auth_user_username_idx ON auth_user(username);
