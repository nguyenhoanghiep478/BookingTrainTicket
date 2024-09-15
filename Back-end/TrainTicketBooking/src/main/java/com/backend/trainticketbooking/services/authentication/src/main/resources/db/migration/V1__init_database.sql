CREATE TABLE user_credential (
                                 id SERIAL PRIMARY KEY,
                                 first_name VARCHAR(255) NOT NULL,
                                 last_name VARCHAR(255) NOT NULL,
                                 email VARCHAR(255) NOT NULL,
                                 phone VARCHAR(50),
                                 address VARCHAR(255),
                                 password VARCHAR(255) NOT NULL,
                                 created_date timestamp not null,
                                 last_modified_date timestamp
);

CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      created_date timestamp not null,
                      last_modified_date timestamp
);

CREATE TABLE permission (
                            id Serial PRIMARY KEY,
                            code VARCHAR(255) NOT NULL,
                            group_code VARCHAR(255),
                            created_date timestamp not null,
                            last_modified_date timestamp
);

CREATE TABLE role_permission (
                                 role_id INT,
                                 permission_id INT,
                                 PRIMARY KEY (role_id, permission_id),
                                 CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id),
                                 CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permission(id)
);





CREATE TABLE user_role (
                           user_id INT,
                           role_id INT,
                           PRIMARY KEY (user_id, role_id),
                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_credential(id),
                           CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE OR REPLACE FUNCTION check_create_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.createDate <> OLD.createDate THEN
        RAISE EXCEPTION 'Cannot change the createDate field';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

;CREATE OR REPLACE FUNCTION check_last_modified_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.last_modified_date < OLD.created_date THEN
        RAISE EXCEPTION 'modified date cannot before created date';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

