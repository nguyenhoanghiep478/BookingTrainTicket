ALTER TABLE user_credential ADD COLUMN is_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE user_credential ADD COLUMN is_first_visit BOOLEAN DEFAULT TRUE;


UPDATE user_credential SET is_verified = TRUE WHERE is_verified IS NULL;
UPDATE user_credential SET is_first_visit = FALSE WHERE is_first_visit IS NULL;