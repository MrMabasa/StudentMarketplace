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
                category,
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
            category,
            module_code
        } = req.body;

        if (
            seller_id === undefined ||
            !title ||
            !description ||
            price === undefined ||
            !condition ||
            !category
        ) {
            res.status(400).json({
                message:
                    "Seller, title, description, price, condition and category are required."
            });
            return;
        }

        const [result] = await database.execute(
            `INSERT INTO listings
                (
                    seller_id,
                    title,
                    description,
                    price,
                    \`condition\`,
                    category,
                    module_code
                )
             VALUES (?, ?, ?, ?, ?, ?, ?)`,
            [
                seller_id,
                title,
                description,
                price,
                condition,
                category,
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

// Return one marketplace listing by its ID.
export async function getListingById(
    req: Request,
    res: Response
): Promise<void> {
    try {
        const listingId = Number(req.params.id);

        if (!Number.isInteger(listingId) || listingId <= 0) {
            res.status(400).json({
                message: "A valid listing ID is required."
            });
            return;
        }

        const [rows] = await database.execute(
            `SELECT
                listing_id,
                seller_id,
                title,
                description,
                price,
                \`condition\`,
                category,
                module_code,
                status,
                date_created
             FROM listings
             WHERE listing_id = ?`,
            [listingId]
        );

        const listings = rows as Array<{
            listing_id: number;
            seller_id: number;
            title: string;
            description: string;
            price: number;
            condition: string;
            category: string;
            module_code: string | null;
            status: string;
            date_created: Date;
        }>;

        if (listings.length === 0) {
            res.status(404).json({
                message: "Listing not found."
            });
            return;
        }

        res.status(200).json(listings[0]);
    } catch (error) {
        console.error("Failed to retrieve listing:", error);

        res.status(500).json({
            message: "Failed to retrieve listing."
        });
    }
}

// Update an existing marketplace listing.
export async function updateListing(
    req: Request,
    res: Response
): Promise<void> {
    try {
        const listingId = Number(req.params.id);

        // Validate the listing ID.
        if (!Number.isInteger(listingId) || listingId <= 0) {
            res.status(400).json({
                message: "A valid listing ID is required."
            });
            return;
        }

        const {
            title,
            description,
            price,
            condition,
            category,
            module_code,
            status
        } = req.body;

        // Validate the required fields.
        if (
            !title ||
            !description ||
            price === undefined ||
            !condition ||
            !category
        ) {
            res.status(400).json({
                message:
                    "Title, description, price, condition and category are required."
            });
            return;
        }

        const [result] = await database.execute(
            `UPDATE listings
             SET
                title = ?,
                description = ?,
                price = ?,
                \`condition\` = ?,
                category = ?,
                module_code = ?,
                status = ?
             WHERE listing_id = ?`,
            [
                title,
                description,
                price,
                condition,
                category,
                module_code || null,
                status || "Active",
                listingId
            ]
        );

        const updateResult = result as { affectedRows: number };

        // Return 404 if the listing does not exist.
        if (updateResult.affectedRows === 0) {
            res.status(404).json({
                message: "Listing not found."
            });
            return;
        }

        res.status(200).json({
            message: "Listing updated successfully."
        });
    } catch (error) {
        console.error("Failed to update listing:", error);

        res.status(500).json({
            message: "Failed to update listing."
        });
    }
}

// Delete an existing marketplace listing.
export async function deleteListing(
    req: Request,
    res: Response
): Promise<void> {
    try {
        const listingId = Number(req.params.id);

        // Validate the listing ID.
        if (!Number.isInteger(listingId) || listingId <= 0) {
            res.status(400).json({
                message: "A valid listing ID is required."
            });
            return;
        }

        const [result] = await database.execute(
            `DELETE FROM listings
             WHERE listing_id = ?`,
            [listingId]
        );

        const deleteResult = result as { affectedRows: number };

        // Return 404 if the listing does not exist.
        if (deleteResult.affectedRows === 0) {
            res.status(404).json({
                message: "Listing not found."
            });
            return;
        }

        res.status(200).json({
            message: "Listing deleted successfully."
        });
    } catch (error) {
        console.error("Failed to delete listing:", error);

        res.status(500).json({
            message: "Failed to delete listing."
        });
    }
}