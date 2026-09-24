-- The Student Marketplace database schema.
-- This file creates the core tables for users, listings and favourites.

-- Users store the application's user/account information.
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    student_number VARCHAR(100) NOT NULL UNIQUE,
    verification_status BOOLEAN NOT NULL DEFAULT FALSE
);

-- Listings store items being offered on the marketplace.
CREATE TABLE IF NOT EXISTS listings (
    listing_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    `condition` VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    module_code VARCHAR(100),
    status VARCHAR(50) NOT NULL DEFAULT 'Active',
    date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_listings_seller
        FOREIGN KEY (seller_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- Favourites link users to listings they have saved.
CREATE TABLE IF NOT EXISTS favourites (
    favourite_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    listing_id INT NOT NULL,
    date_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_favourites_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_favourites_listing
        FOREIGN KEY (listing_id)
        REFERENCES listings(listing_id)
        ON DELETE CASCADE,

    CONSTRAINT unique_user_listing_favourite
        UNIQUE (user_id, listing_id)
);