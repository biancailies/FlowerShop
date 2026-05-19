-- =============================================================
-- Script SQL pentru UserManagementService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS user_management_db;
USE user_management_db;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    flower_shop_id INT NULL
);

-- NU se pune foreign key către inventory_db.flower_shops!
-- Între microservicii nu facem foreign key direct între baze de date.
-- flower_shop_id este doar ID logic către InventoryService.

-- Date inițiale de test
INSERT INTO users (username, password, type, flower_shop_id) VALUES
('admin', 'admin123', 'ADMIN', NULL),
('manager1', 'manager123', 'MANAGER', NULL),
('employee1', 'employee123', 'EMPLOYEE', 1),
('employee2', 'employee123', 'EMPLOYEE', 2),
('client1', 'client123', 'CLIENT', NULL);
