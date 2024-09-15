
CREATE OR REPLACE FUNCTION check_create_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.createDate <> OLD.createDate THEN
        RAISE EXCEPTION 'Cannot change the createDate field';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_last_modified_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.last_modified_date < OLD.createDate THEN
        RAISE EXCEPTION 'modified date cannot before created date';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;