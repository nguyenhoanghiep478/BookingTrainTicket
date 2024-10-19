alter table user_credential
    add column if not exists fail_attempt int default 0;

alter table user_credential
    add column if not exists lock_time TIMESTAMP without time zone