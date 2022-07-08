ALTER TABLE robot
ADD COLUMN created_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN created_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modified_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN modified_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modify_process text NOT NULL DEFAULT '<unknown>';

ALTER TABLE robot
ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE robot
ALTER COLUMN modified_by DROP DEFAULT;
ALTER TABLE robot
ALTER COLUMN modify_process DROP DEFAULT;


ALTER TABLE competition
ADD COLUMN created_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN created_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modified_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN modified_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modify_process text NOT NULL DEFAULT '<unknown>';

ALTER TABLE competition
ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE competition
ALTER COLUMN modified_by DROP DEFAULT;
ALTER TABLE competition
ALTER COLUMN modify_process DROP DEFAULT;


ALTER TABLE run
ADD COLUMN created_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN created_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modified_at timestamp with time zone NOT NULL DEFAULT current_timestamp,
ADD COLUMN modified_by varchar(60) NOT NULL DEFAULT '<unknown>',
ADD COLUMN modify_process text NOT NULL DEFAULT '<unknown>';

ALTER TABLE run
ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE run
ALTER COLUMN modified_by DROP DEFAULT;
ALTER TABLE run
ALTER COLUMN modify_process DROP DEFAULT;
