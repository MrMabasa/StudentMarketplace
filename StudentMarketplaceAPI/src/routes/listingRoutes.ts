import { Router } from "express";
import { getListings } from "../controllers/listingController";

// Create the listings router.
const router = Router();

// GET /listings - return all marketplace listings.
router.get("/", getListings);

export default router;