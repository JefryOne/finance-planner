CREATE TABLE IF NOT EXISTS expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item VARCHAR(255),
    amount DECIMAL(10, 2)
);