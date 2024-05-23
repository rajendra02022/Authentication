-- Insert initial data into the users table
INSERT INTO users (username, password, role) VALUES ('admin', 'adminpassword', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('user', 'userpassword', 'USER');
INSERT INTO users (username, password, role) VALUES ('manager', 'managerpassword', 'MANAGER');
INSERT INTO users (username, password, role) VALUES ('employee1', 'employeepassword1', 'EMPLOYEE');
INSERT INTO users (username, password, role) VALUES ('employee2', 'employeepassword2', 'EMPLOYEE');
INSERT INTO users (username, password, role) VALUES ('guest', 'guestpassword', 'GUEST');
INSERT INTO users (username, password, role) VALUES ('superadmin', 'superadminpassword', 'SUPERADMIN');
INSERT INTO users (username, password, role) VALUES ('developer', 'developerpassword', 'DEVELOPER');
INSERT INTO users (username, password, role) VALUES ('tester', 'testerpassword', 'TESTER');
INSERT INTO users (username, password, role) VALUES ('analyst', 'analystpassword', 'ANALYST');

-- Insert roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('MANAGER');
-- Add more roles as needed

-- Map users to roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin -> ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- user -> USER
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- manager -> MANAGER
-- Add more user-role mappings as needed

-- Insert initial data into the profiles table
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Admin', 'User', '1234567890', '123 Admin St', 1);
INSERT INTO profiles (first_name, last_name, phone_number, address, user_id) VALUES ('Regular', 'User', '0987654321', '456 User Ave', 2);
-- Add more profiles as needed
