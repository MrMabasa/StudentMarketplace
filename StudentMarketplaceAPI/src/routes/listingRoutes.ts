import { Router } from "express";
import {
    getListings,
    getListingById,
    createListing,
    updateListing
} from "../controllers/listingController";

// Create the listings router.
const router = Router();

// GET /listings - return all marketplace listings.
router.get("/", getListings);

// GET /listings/:id - return one marketplace listing.
router.get("/:id", getListingById);

// POST /listings - create a new marketplace listing.
router.post("/", createListing);

// PUT /listings/:id - update an existing marketplace listing.
router.put("/:id", updateListing);

export default router;