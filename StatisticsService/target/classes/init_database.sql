-- =============================================================
-- Script SQL pentru StatisticsService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS statistics_service_db;
USE statistics_service_db;

DROP TABLE IF EXISTS Statistics;

CREATE TABLE IF NOT EXISTS Statistics (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    FlowerShopId INT NOT NULL,
    Statistics FLOAT NOT NULL,
    Type VARCHAR(100) NOT NULL
);

-- Date inițiale de test
INSERT INTO Statistics (FlowerShopId, Statistics, Type) VALUES
(1, 120, 'Total flowers sold'),
(1, 35, 'Low stock products'),
(2, 250, 'Total flowers sold'),
(2, 70, 'Available products');
