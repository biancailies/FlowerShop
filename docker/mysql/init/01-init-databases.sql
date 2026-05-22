CREATE DATABASE IF NOT EXISTS flower_catalog_db;
CREATE DATABASE IF NOT EXISTS inventory_db;
CREATE DATABASE IF NOT EXISTS user_management_db;
CREATE DATABASE IF NOT EXISTS statistics_service_db;
CREATE DATABASE IF NOT EXISTS notification_db;

USE flower_catalog_db;

CREATE TABLE IF NOT EXISTS flowers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    purchase_price FLOAT NOT NULL,
    selling_price FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flower_id INT NOT NULL,
    url LONGTEXT NOT NULL,
    FOREIGN KEY (flower_id) REFERENCES flowers(id) ON DELETE CASCADE
);

INSERT INTO flowers (id, name, purchase_price, selling_price) VALUES
(1, 'Trandafir rosu', 5.50, 12.00),
(2, 'Lalea galbena', 3.00, 8.50),
(3, 'Crizantema alba', 4.00, 9.00),
(4, 'Orhidee mov', 15.00, 35.00),
(5, 'Floarea soarelui', 2.50, 7.00)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    purchase_price = VALUES(purchase_price),
    selling_price = VALUES(selling_price);

INSERT INTO images (id, flower_id, url) VALUES
(1, 1, 'https://example.com/images/trandafir_rosu.jpg'),
(2, 1, 'https://example.com/images/trandafir_rosu_2.jpg'),
(3, 2, 'https://example.com/images/lalea_galbena.jpg'),
(4, 3, 'https://example.com/images/crizantema_alba.jpg'),
(5, 4, 'https://example.com/images/orhidee_mov.jpg'),
(6, 4, 'https://example.com/images/orhidee_mov_2.jpg'),
(7, 5, 'https://example.com/images/floarea_soarelui.jpg')
ON DUPLICATE KEY UPDATE
    flower_id = VALUES(flower_id),
    url = VALUES(url);

USE inventory_db;

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

INSERT INTO flower_shops (id, name, address) VALUES
(1, 'Floraria Magnolia', 'Cluj-Napoca, Str. Memorandumului 12'),
(2, 'Floraria Iris', 'Bucuresti, Calea Victoriei 45'),
(3, 'Floraria Green Garden', 'Timisoara, Bd. Eroilor 8')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    address = VALUES(address);

INSERT INTO stocks (id, flower_shop_id, flower_id, color, quantity) VALUES
(1, 1, 1, 'Rosu', 20),
(2, 1, 2, 'Galben', 15),
(3, 2, 1, 'Alb', 10),
(4, 2, 3, 'Alb', 30),
(5, 3, 4, 'Mov', 12),
(6, 3, 5, 'Galben', 18)
ON DUPLICATE KEY UPDATE
    flower_shop_id = VALUES(flower_shop_id),
    flower_id = VALUES(flower_id),
    color = VALUES(color),
    quantity = VALUES(quantity);

USE user_management_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    flower_shop_id INT NULL
);

INSERT INTO users (id, username, password, type, flower_shop_id) VALUES
(1, 'admin', 'admin123', 'ADMIN', NULL),
(2, 'manager1', 'manager123', 'MANAGER', NULL),
(3, 'employee1', 'employee123', 'EMPLOYEE', 1),
(4, 'employee2', 'employee123', 'EMPLOYEE', 2),
(5, 'client1', 'client123', 'CLIENT', NULL)
ON DUPLICATE KEY UPDATE
    username = VALUES(username),
    password = VALUES(password),
    type = VALUES(type),
    flower_shop_id = VALUES(flower_shop_id);

USE statistics_service_db;

CREATE TABLE IF NOT EXISTS Statistics (
    `Id` INT AUTO_INCREMENT PRIMARY KEY,
    `FlowerShopId` INT NOT NULL,
    `Statistics` FLOAT NOT NULL,
    `Type` VARCHAR(100) NOT NULL
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

INSERT INTO Statistics (`Id`, `FlowerShopId`, `Statistics`, `Type`) VALUES
(1, 1, 1500.50, 'DAILY_REVENUE'),
(2, 2, 850.00, 'DAILY_REVENUE'),
(3, 1, 450.25, 'DAILY_PROFIT')
ON DUPLICATE KEY UPDATE
    `FlowerShopId` = VALUES(`FlowerShopId`),
    `Statistics` = VALUES(`Statistics`),
    `Type` = VALUES(`Type`);

INSERT INTO sale_statistics (id, flower_id, flower_name, flower_shop_id, quantity_sold, revenue, profit, sale_date) VALUES
(1, 1, 'Trandafir Rosu', 1, 10, 150.0, 50.0, NOW()),
(2, 2, 'Lalea Galbena', 2, 5, 25.0, 10.0, NOW())
ON DUPLICATE KEY UPDATE
    flower_id = VALUES(flower_id),
    flower_name = VALUES(flower_name),
    flower_shop_id = VALUES(flower_shop_id),
    quantity_sold = VALUES(quantity_sold),
    revenue = VALUES(revenue),
    profit = VALUES(profit);

USE notification_db;

CREATE TABLE IF NOT EXISTS notifications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL,
    recipient_email VARCHAR(255),
    recipient_discord_channel_id VARCHAR(100)
);

INSERT INTO notifications (id, user_id, message, type) VALUES
(1, 1, 'Contul admin a fost actualizat.', 'EMAIL'),
(2, 2, 'Datele managerului au fost modificate.', 'DISCORD')
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    message = VALUES(message),
    type = VALUES(type);
