CREATE TABLE special_user
(
    username varchar(50) PRIMARY KEY,
    password text NOT NULL,
    version bigint NOT NULL
);
