CREATE TABLE refresh_token_whitelist
(
    token_id uuid PRIMARY KEY,
    username varchar(60) NOT NULL,
    expired_at timestamp without time zone
);

CREATE INDEX refresh_token_whitelist_username_idx ON refresh_token_whitelist(username);
