import fs from "fs";
import path from "path";
import { database } from "./database";

// Folder containing database migration files.
const migrationsPath = path.join(process.cwd(), "src", "config", "migrations");

async function runMigrations(): Promise<void> {
    const connection = await database.getConnection();

    try {
        // Create a table that records which migrations have already run.
        await connection.query(`
            CREATE TABLE IF NOT EXISTS schema_migrations (
                migration_id INT AUTO_INCREMENT PRIMARY KEY,
                migration_name VARCHAR(255) NOT NULL UNIQUE,
                applied_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
            )
        `);

        // Find all SQL migration files and run them in filename order.
        const migrationFiles = fs
            .readdirSync(migrationsPath)
            .filter((file) => file.endsWith(".sql"))
            .sort();

        for (const migrationFile of migrationFiles) {
            const [rows] = await connection.query(
                `
                SELECT migration_id
                FROM schema_migrations
                WHERE migration_name = ?
                `,
                [migrationFile]
            );

            const appliedMigrations = rows as { migration_id: number }[];

            if (appliedMigrations.length > 0) {
                console.log(`Skipping already applied migration: ${migrationFile}`);
                continue;
            }

            const migrationPath = path.join(migrationsPath, migrationFile);
            const migrationSql = fs.readFileSync(migrationPath, "utf8").trim();

            console.log(`Running migration: ${migrationFile}`);

            await connection.beginTransaction();

            try {
                await connection.query(migrationSql);

                await connection.query(
                    `
                    INSERT INTO schema_migrations (migration_name)
                    VALUES (?)
                    `,
                    [migrationFile]
                );

                await connection.commit();

                console.log(`Migration completed: ${migrationFile}`);
            } catch (error) {
                await connection.rollback();
                throw error;
            }
        }

        console.log("Database migrations completed successfully.");
    } catch (error) {
        console.error("Database migration failed:", error);
        process.exitCode = 1;
    } finally {
        connection.release();
        await database.end();
    }
}

runMigrations();