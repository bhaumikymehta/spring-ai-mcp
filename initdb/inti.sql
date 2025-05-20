-- initdb/init.sql
-- This script will be executed when the PostgreSQL container starts for the first time.
-- It creates a sample table and inserts some data.

-- Drop table if it exists to ensure a clean setup
DROP TABLE IF EXISTS products;

-- Create a 'products' table
CREATE TABLE products (
    id SERIAL PRIMARY KEY,          -- Auto-incrementing integer primary key
    name VARCHAR(255) NOT NULL,     -- Product name, cannot be null
    category VARCHAR(100),          -- Product category
    price DECIMAL(10, 2),           -- Product price with 2 decimal places
    description TEXT                -- Product description
);

-- Insert some sample data into the 'products' table
INSERT INTO products (name, category, price, description) VALUES
('Laptop Pro 15', 'Electronics', 1200.00, 'High-performance laptop with 16GB RAM and 512GB SSD.'),
('Wireless Mouse', 'Accessories', 25.50, 'Ergonomic wireless mouse with 5 buttons.'),
('Mechanical Keyboard', 'Accessories', 75.00, 'RGB backlit mechanical keyboard with blue switches.'),
('4K Monitor 27"', 'Electronics', 350.00, '27-inch 4K UHD monitor with HDR support.'),
('USB-C Hub', 'Accessories', 40.00, '7-in-1 USB-C hub with HDMI, USB 3.0, and SD card reader.'),
('Smartphone X', 'Electronics', 799.99, 'Latest generation smartphone with advanced camera features.'),
('Smart Watch Series 5', 'Wearables', 299.00, 'Feature-rich smartwatch with health tracking.'),
('Bluetooth Headphones', 'Audio', 150.00, 'Over-ear Bluetooth headphones with noise cancellation.'),
('Gaming Console NextGen', 'Gaming', 499.00, 'Next-generation gaming console with 8K support.'),
('Office Chair Deluxe', 'Furniture', 220.00, 'Ergonomic office chair with lumbar support.');

-- You can add more tables and data as needed for your application.
