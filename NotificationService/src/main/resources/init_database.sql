-- =============================================================
-- Script SQL pentru NotificationService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS notification_db;
USE notification_db;

DROP TABLE IF EXISTS notifications;

CREATE TABLE IF NOT EXISTS notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL
);

-- Important: Nu se pune foreign key către user_management_db.users.
-- Între microservicii nu facem foreign key direct între baze de date.
-- user_id este doar ID logic către UserManagementService.

-- Date de test
INSERT INTO notifications (user_id, message, type) VALUES
(1, 'Contul admin a fost actualizat.', 'EMAIL'),
(2, 'Datele managerului au fost modificate.', 'DISCORD');
