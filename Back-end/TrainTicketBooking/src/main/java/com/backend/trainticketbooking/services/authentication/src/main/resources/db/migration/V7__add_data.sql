insert into user_credential (first_name, last_name, email, phone, password,created_date,last_modified_date) values ('Nguyễn','Hiệp','nguyenhoanghiep478@gmail.com','0965478891','$2a$10$4fqq2ouWE5TpX./iO4fPruCvQgkIlUMPaYiRRnK2AxsBIzZ5GTBwK',current_timestamp,current_timestamp);

insert into permission(code, group_code, created_date, last_modified_date) values ('ACCOUNTING_MANAGER','CREATE_ACCOUNT UPDATE_ACCOUNT BLOCK_ACCOUNT',current_timestamp,current_timestamp);
insert into permission(code, group_code, created_date, last_modified_date) values ('TICKET_MANAGER','CREATE_TICKET UPDATE_TICKET',current_timestamp,current_timestamp);

insert into role(name, created_date, last_modified_date) values ('ACCOUNTING MANAGER',current_timestamp,current_timestamp);
insert into role(name, created_date, last_modified_date) values ('TICKET MANAGER',current_timestamp,current_timestamp);
insert into role(name, created_date, last_modified_date) values ('GLOBAL ADMIN',current_timestamp,current_timestamp);

insert into role_permission(role_id, permission_id) VALUES (1,1);
insert into role_permission(role_id, permission_id) VALUES (2,2);
insert into role_permission(role_id, permission_id) VALUES (3,1);
insert into role_permission(role_id, permission_id) VALUES (3,2);

insert into user_role(user_id, role_id) VALUES (1,3);


