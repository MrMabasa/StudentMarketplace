import { Router } from "express";
import {
    getListings,
    createListing
} from "../controllers/listingController";

// Create the listings router.
const router = Router();

// GET /listings - return all marketplace listings.
router.get("/", getListings);

// POST /listings - create a new marketplace listing.
router.post("/", createListing);

export default router;