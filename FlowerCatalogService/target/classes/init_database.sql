-- =============================================================
-- Script SQL pentru FlowerCatalogService
-- Rulează acest script în MySQL înainte de a porni aplicația
-- =============================================================

CREATE DATABASE IF NOT EXISTS flower_catalog_db;
USE flower_catalog_db;

DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS flowers;

CREATE TABLE IF NOT EXISTS flowers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    purchase_price FLOAT NOT NULL,
    selling_price FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flower_id INT NOT NULL,
    url VARCHAR(500) NOT NULL,
    FOREIGN KEY (flower_id) REFERENCES flowers(id) ON DELETE CASCADE
);

-- Date inițiale de test
INSERT INTO flowers (name, purchase_price, selling_price) VALUES
('Trandafir rosu', 5.50, 12.00),
('Lalea galbena', 3.00, 8.50),
('Crizantema alba', 4.00, 9.00),
('Orhidee mov', 15.00, 35.00),
('Floarea soarelui', 2.50, 7.00);

INSERT INTO images (flower_id, url) VALUES
(1, 'https://example.com/images/trandafir_rosu.jpg'),
(1, 'https://example.com/images/trandafir_rosu_2.jpg'),
(2, 'https://example.com/images/lalea_galbena.jpg'),
(3, 'https://example.com/images/crizantema_alba.jpg'),
(4, 'https://example.com/images/orhidee_mov.jpg'),
(4, 'https://example.com/images/orhidee_mov_2.jpg'),
(5, 'https://example.com/images/floarea_soarelui.jpg');
