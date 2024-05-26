-- Insert initial data into the users table
INSERT INTO users (username, password, email) VALUES ('admin', '$2a$10$eMGM1x6ouoROLE/YZgflD.0x49RxUZ.ST8FYLoYYJEcog21CHXRo.', 'admin@example.com');
INSERT INTO users (username, password, email) VALUES ('user', 'userpassword', 'user@example.com');
INSERT INTO users (username, password, email) VALUES ('manager', 'managerpassword', 'manager@example.com');
INSERT INTO users (username, password, email) VALUES ('employee1', 'employeepassword1', 'employee1@example.com');
INSERT INTO users (username, password, email) VALUES ('employee2', 'employeepassword2', 'employee2@example.com');
INSERT INTO users (username, password, email) VALUES ('guest', 'guestpassword', 'guest@example.com');
INSERT INTO users (username, password, email) VALUES ('superadmin', 'superadminpassword', 'superadmin@example.com');
INSERT INTO users (username, password, email) VALUES ('developer', 'developerpassword', 'developer@example.com');
INSERT INTO users (username, password, email) VALUES ('tester', 'testerpassword', 'tester@example.com');
INSERT INTO users (username, password, email) VALUES ('analyst', 'analystpassword', 'analyst@example.com');

-- Insert roles
INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');
INSERT INTO roles (id, name) VALUES (3, 'MANAGER');
INSERT INTO roles (id, name) VALUES (4, 'EMPLOYEE');
INSERT INTO roles (id, name) VALUES (5, 'GUEST');
INSERT INTO roles (id, name) VALUES (6, 'SUPERADMIN');
INSERT INTO roles (id, name) VALUES (7, 'DEVELOPER');
INSERT INTO roles (id, name) VALUES (8, 'TESTER');
INSERT INTO roles (id, name) VALUES (9, 'ANALYST');

-- Map users to roles
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'admin'), 1); -- admin -> ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'user'), 2); -- user -> USER
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'manager'), 3); -- manager -> MANAGER
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'employee1'), 4); -- employee1 -> EMPLOYEE
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'employee2'), 4); -- employee2 -> EMPLOYEE
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'guest'), 5); -- guest -> GUEST
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'superadmin'), 6); -- superadmin -> SUPERADMIN
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'developer'), 7); -- developer -> DEVELOPER
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'tester'), 8); -- tester -> TESTER
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'analyst'), 9); -- analyst -> ANALYST

-- Insert initial data into the profiles table
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Admin', 'User', '1234567890', '123 Admin St', (SELECT id FROM users WHERE username = 'admin'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Regular', 'User', '0987654321', '456 User Ave', (SELECT id FROM users WHERE username = 'user'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Manager', 'User', '1122334455', '789 Manager Blvd', (SELECT id FROM users WHERE username = 'manager'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Employee', 'One', '2233445566', '101 Employee St', (SELECT id FROM users WHERE username = 'employee1'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Employee', 'Two', '3344556677', '102 Employee St', (SELECT id FROM users WHERE username = 'employee2'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Guest', 'User', '4455667788', '103 Guest Ave', (SELECT id FROM users WHERE username = 'guest'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Super', 'Admin', '5566778899', '104 Superadmin Blvd', (SELECT id FROM users WHERE username = 'superadmin'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Dev', 'User', '6677889900', '105 Developer St', (SELECT id FROM users WHERE username = 'developer'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Test', 'User', '7788990011', '106 Tester Ave', (SELECT id FROM users WHERE username = 'tester'));
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Analyst', 'User', '8899001122', '107 Analyst Blvd', (SELECT id FROM users WHERE username = 'analyst'));