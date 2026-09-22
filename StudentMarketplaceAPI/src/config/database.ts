import mysql from "mysql2/promise";
import dotenv from "dotenv";
import fs from "fs";
import path from "path";

// Load the variables from the .env file.
dotenv.config();

// Check that all required database settings exist.
const requiredEnvironmentVariables = [
    "DB_HOST",
    "DB_PORT",
    "DB_USER",
    "DB_PASSWORD",
    "DB_NAME"
];

for (const variable of requiredEnvironmentVariables) {
    if (!process.env[variable]) {
        throw new Error(`Missing environment variable: ${variable}`);
    }
}

// Load Aiven's CA certificate for secure SSL communication.
const caCertificate = fs.readFileSync(
    path.join(process.cwd(), "aiven-ca.pem")
);

// Create a connection pool for the Aiven MySQL database.
export const database = mysql.createPool({
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,

    // Aiven requires an encrypted SSL connection.
    ssl: {
        ca: caCertificate,
        rejectUnauthorized: true
    },

    // Keep a small pool suitable for our application.
    connectionLimit: 5
});

// Test the database connection.
export async function testDatabaseConnection(): Promise<void> {
    const connection = await database.getConnection();

    try {
        await connection.query("SELECT 1");
        console.log("Successfully connected to the Aiven MySQL database.");
    } finally {
        connection.release();
    }
}