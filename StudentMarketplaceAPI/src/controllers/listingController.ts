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

// Create a new marketplace listing.
export async function createListing(
    req: Request,
    res: Response
): Promise<void> {
    try {
        const {
            seller_id,
            title,
            description,
            price,
            condition,
            module_code
        } = req.body;

        // Validate the required listing fields.
        if (
            seller_id === undefined ||
            !title ||
            !description ||
            price === undefined ||
            !condition
        ) {
            res.status(400).json({
                message: "Seller, title, description, price and condition are required."
            });
            return;
        }

        const [result] = await database.execute(
            `INSERT INTO listings
                (seller_id, title, description, price, \`condition\`, module_code)
             VALUES (?, ?, ?, ?, ?, ?)`,
            [
                seller_id,
                title,
                description,
                price,
                condition,
                module_code || null
            ]
        );

        const insertResult = result as { insertId: number };

        res.status(201).json({
            message: "Listing created successfully.",
            listing_id: insertResult.insertId
        });
    } catch (error) {
        console.error("Failed to create listing:", error);

        res.status(500).json({
            message: "Failed to create listing."
        });
    }
}