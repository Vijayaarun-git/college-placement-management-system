-- College Placement Management System
-- Run this whole script once in MySQL Workbench.

CREATE DATABASE IF NOT EXISTS placement_db;
USE placement_db;

CREATE TABLE students(
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    department VARCHAR(50),
    cgpa DECIMAL(3,2),
    backlogs INT DEFAULT 0,
    skills VARCHAR(255)
);

CREATE TABLE companies(
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    contact VARCHAR(100)
);

CREATE TABLE jobs(
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    role VARCHAR(100) NOT NULL,
    package_lpa DECIMAL(5,2),
    min_cgpa DECIMAL(3,2),
    max_backlogs INT DEFAULT 0,
    allowed_departments VARCHAR(255),
    required_skills VARCHAR(255),
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
);

CREATE TABLE applications(
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    job_id INT NOT NULL,
    status VARCHAR(30) DEFAULT 'Applied',
    applied_date DATE,
    UNIQUE (student_id, job_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
);

CREATE TABLE interviews(
    interview_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT NOT NULL UNIQUE,
    interview_date DATE NOT NULL,
    interview_time TIME NOT NULL,
    venue VARCHAR(100),
    result VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (application_id) REFERENCES applications(application_id)
);

CREATE TABLE placements(
    placement_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL UNIQUE,
    job_id INT NOT NULL,
    package_lpa DECIMAL(5,2),
    placed_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
);

CREATE TABLE admin(
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Default admin (username: admin, password: admin123)
INSERT INTO admin (username, password) VALUES ('admin', SHA2('admin123', 256));

-- Sample data (all student passwords: pass123)
INSERT INTO students (name, email, password, phone, department, cgpa, backlogs, skills) VALUES
('Arun Kumar', 'arun@gmail.com', SHA2('pass123', 256), '9876543210', 'AI&DS', 8.50, 0, 'Java, SQL'),
('Priya S', 'priya@gmail.com', SHA2('pass123', 256), '9123456780', 'CSE', 7.20, 1, 'Python, SQL'),
('Kavin R', 'kavin@gmail.com', SHA2('pass123', 256), '9000000001', 'IT', 8.10, 0, 'Java, C');

INSERT INTO companies (name, location, contact) VALUES
('Infosys', 'Bangalore', 'hr@infosys.com');

INSERT INTO jobs (company_id, role, package_lpa, min_cgpa, max_backlogs, allowed_departments, required_skills) VALUES
(1, 'Software Engineer', 6.50, 7.50, 0, 'AI&DS,CSE,IT', 'Java, SQL');
