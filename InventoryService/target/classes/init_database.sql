-- =============================================================
-- Script SQL pentru InventoryService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

DROP TABLE IF EXISTS stocks;
DROP TABLE IF EXISTS flower_shops;

CREATE TABLE IF NOT EXISTS flower_shops (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS stocks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flower_shop_id INT NOT NULL,
    flower_id INT NOT NULL,
    color VARCHAR(50),
    quantity INT,
    FOREIGN KEY (flower_shop_id) REFERENCES flower_shops(id) ON DELETE CASCADE
);

-- NU se pune foreign key către flowers din flower_catalog_db!
-- Legătura cu floarea este logică prin flower_id și REST API.

-- Date inițiale de test: 3 florării
INSERT INTO flower_shops (name, address) VALUES
('Floraria Magnolia', 'Cluj-Napoca, Str. Memorandumului 12'),
('Floraria Iris', 'Bucuresti, Calea Victoriei 45'),
('Floraria Green Garden', 'Timisoara, Bd. Eroilor 8');

-- Stocuri (flower_id-urile corespund florilor din FlowerCatalogService)
INSERT INTO stocks (flower_shop_id, flower_id, color, quantity) VALUES
(1, 1, 'Rosu', 20),
(1, 2, 'Galben', 15),
(2, 1, 'Alb', 10),
(2, 3, 'Alb', 30),
(3, 4, 'Mov', 12),
(3, 5, 'Galben', 18);
