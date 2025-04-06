BEGIN;
ALTER TABLE client ADD COLUMN IF NOT EXISTS sex_string VARCHAR(255);
UPDATE client SET sex_string = 'MALE' WHERE sex = 0;
UPDATE client SET sex_string = 'FEMALE' WHERE sex = 1;
ALTER TABLE client DROP COLUMN sex;
ALTER TABLE client RENAME COLUMN sex_string TO sex;

ALTER TABLE client_profile ADD COLUMN IF NOT EXISTS sex_string VARCHAR(255);
UPDATE client_profile SET sex_string = 'MALE' WHERE sex = 0;
UPDATE client_profile SET sex_string = 'FEMALE' WHERE sex = 1;
ALTER TABLE client_profile DROP COLUMN sex;
ALTER TABLE client_profile RENAME COLUMN sex_string TO sex;

ALTER TABLE contact ADD COLUMN IF NOT EXISTS contact_type_string VARCHAR(255);
UPDATE contact SET contact_type_string = 'HOME' WHERE contact_type = 0;
UPDATE contact SET contact_type_string = 'ADDITIONAL' WHERE contact_type = 1;
UPDATE contact SET contact_type_string = 'MOBILE' WHERE contact_type = 2;
UPDATE contact SET contact_type_string = 'WORK' WHERE contact_type = 3;
UPDATE contact SET contact_type_string = 'REGISTRATION_PLACE' WHERE contact_type = 4;
ALTER TABLE contact DROP COLUMN contact_type;
ALTER TABLE contact RENAME COLUMN contact_type_string TO contact_type;

ALTER TABLE credit ADD COLUMN IF NOT EXISTS stage_string VARCHAR(255);
UPDATE credit SET stage_string = 'CREDIT_FORM' WHERE stage = 0;
UPDATE credit SET stage_string = 'CREDIT_APPLICATION' WHERE stage = 1;
UPDATE credit SET stage_string = 'AGREEMENT_SIGNING' WHERE stage = 2;
UPDATE credit SET stage_string = 'REJECT' WHERE stage = 3;
UPDATE credit SET stage_string = 'COMPLETED' WHERE stage = 4;
ALTER TABLE credit DROP COLUMN stage;
ALTER TABLE credit RENAME COLUMN stage_string TO stage;
COMMIT;
