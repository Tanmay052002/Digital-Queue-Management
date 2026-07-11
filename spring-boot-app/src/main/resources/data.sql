-- demo login user (username: student / password: student123)
INSERT INTO app_user (name, phone, username, password) VALUES ('Test Student', '9999999999', 'student', 'student123');

-- the two offices shown as boxes on the dashboard
INSERT INTO office (name, location) VALUES ('Hospital', 'City Center');
INSERT INTO office (name, location) VALUES ('RTO', 'Main Road');

-- counters for Hospital (office_id = 1)
INSERT INTO queue_counter (office_id, counter_number, max_slots) VALUES (1, 1, 5);
INSERT INTO queue_counter (office_id, counter_number, max_slots) VALUES (1, 2, 5);

-- counters for RTO (office_id = 2)
INSERT INTO queue_counter (office_id, counter_number, max_slots) VALUES (2, 1, 5);
INSERT INTO queue_counter (office_id, counter_number, max_slots) VALUES (2, 2, 5);

-- services offered by Hospital
INSERT INTO service (office_id, name) VALUES (1, 'General Checkup');
INSERT INTO service (office_id, name) VALUES (1, 'Blood Test');
INSERT INTO service (office_id, name) VALUES (1, 'X-Ray');

-- services offered by RTO
INSERT INTO service (office_id, name) VALUES (2, 'Driving License');
INSERT INTO service (office_id, name) VALUES (2, 'Vehicle Registration');
INSERT INTO service (office_id, name) VALUES (2, 'Fitness Certificate');
