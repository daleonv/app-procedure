CREATE DATABASE IF NOT EXISTS test_schema;
USE test_schema;

-- Table for Customer which inherits from Person
CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    status ENUM('Activo', 'Inactivo') NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Hombre', 'Mujer', 'Otros') NOT NULL,
    age INT NOT NULL,
    identification VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table for Account
CREATE TABLE Account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    account_type ENUM('Ahorros', 'Corriente') NOT NULL,
    initial_balance DECIMAL(10, 2) NOT NULL,
    status ENUM('Activo', 'Inactivo') NOT NULL,
    customer_id INT,  -- Foreign key to Customer
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table for Transactions
CREATE TABLE Transaction (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    transaction_type ENUM('Deposito', 'Retiro') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    account_id INT,  -- Foreign key to Account
    FOREIGN KEY (account_id) REFERENCES Account(account_id),
    status ENUM('Activo', 'Inactivo') NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
