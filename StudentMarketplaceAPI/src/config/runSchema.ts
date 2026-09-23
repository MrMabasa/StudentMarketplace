import fs from "fs";
import path from "path";
import { database } from "./database";

// Read the SQL schema file.
const schemaPath = path.join(process.cwd(), "src", "config", "schema.sql");
const schema = fs.readFileSync(schemaPath, "utf8");

// Split the schema into individual SQL statements.
const statements = schema
    .split(";")
    .map((statement) => statement.trim())
    .filter((statement) => statement.length > 0);

// Execute each SQL statement separately.
async function runSchema(): Promise<void> {
    try {
        for (const statement of statements) {
            await database.query(statement);
        }

        console.log("Database schema created successfully.");
    } catch (error) {
        console.error("Failed to create the database schema:", error);
    } finally {
        await database.end();
    }
}

runSchema();