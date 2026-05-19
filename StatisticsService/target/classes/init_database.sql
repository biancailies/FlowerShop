-- =============================================================
-- Script SQL pentru StatisticsService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS statistics_service_db;
USE statistics_service_db;

DROP TABLE IF EXISTS Statistics;

CREATE TABLE IF NOT EXISTS Statistics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flower_shop_id INT NOT NULL,
    statistics FLOAT NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS sale_statistics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flower_id INT NOT NULL,
    flower_name VARCHAR(100) NOT NULL,
    flower_shop_id INT NOT NULL,
    quantity_sold INT NOT NULL,
    revenue FLOAT NOT NULL,
    profit FLOAT NOT NULL,
    sale_date DATETIME NOT NULL
);

INSERT INTO Statistics (flower_shop_id, statistics, type) VALUES
(1, 1500.50, 'DAILY_REVENUE'),
(2, 850.00, 'DAILY_REVENUE'),
(1, 450.25, 'DAILY_PROFIT');

INSERT INTO sale_statistics (flower_id, flower_name, flower_shop_id, quantity_sold, revenue, profit, sale_date) VALUES
(1, 'Trandafir Rosu', 1, 10, 150.0, 50.0, NOW()),
(2, 'Lalea Galbena', 2, 5, 25.0, 10.0, NOW());
