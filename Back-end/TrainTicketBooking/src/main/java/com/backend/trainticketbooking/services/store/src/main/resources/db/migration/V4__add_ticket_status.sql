alter table ticket add column if not exists status varchar(50) default 'IN_PROGRESS',
    add column if not exists customer_name varchar(255),
    add column if not exists email varchar(255)