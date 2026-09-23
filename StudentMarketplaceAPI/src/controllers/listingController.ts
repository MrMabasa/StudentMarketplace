import { Request, Response } from "express";
import { database } from "../config/database";

// Return all marketplace listings from the database.
export async function getListings(
    _req: Request,
    res: Response
): Promise<void> {
    try {
        const [rows] = await database.query(
            `SELECT
                listing_id,
                seller_id,
                title,
                description,
                price,
                \`condition\`,
                module_code,
                status,
                date_created
             FROM listings
             ORDER BY date_created DESC`
        );

        res.status(200).json(rows);
    } catch (error) {
        console.error("Failed to retrieve listings:", error);

        res.status(500).json({
            message: "Failed to retrieve listings."
        });
    }
}