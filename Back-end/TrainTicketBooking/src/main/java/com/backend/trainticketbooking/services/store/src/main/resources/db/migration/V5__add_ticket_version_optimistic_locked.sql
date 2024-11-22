ALTER TABLE seat
    ADD COLUMN version BIGINT DEFAULT 0;

CREATE OR REPLACE FUNCTION increment_version()
RETURNS TRIGGER AS $$
BEGIN
    NEW.version := OLD.version + 1;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_increment_version
    BEFORE UPDATE ON seat
    FOR EACH ROW
    EXECUTE FUNCTION increment_version();