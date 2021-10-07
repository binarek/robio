CREATE TABLE refresh_token_whitelist
(
    token_id uuid PRIMARY KEY,
    user_id uuid NOT NULL,
    expired_at timestamp without time zone
);

CREATE INDEX refresh_token_whitelist_user_id_idx ON refresh_token_whitelist(user_id);
