import express from "express";
import cors from "cors";
import { testDatabaseConnection } from "./config/database";
import listingRoutes from "./routes/listingRoutes";

// Create the Express application.
const app = express();

// Allow the Android app to communicate with the API.
app.use(cors());

// Allow the API to receive JSON request bodies.
app.use(express.json());

// Basic test endpoint.
app.get("/", (_req, res) => {
    res.json({
        message: "The Student Marketplace API is running."
    });
});

// Marketplace listing routes.
app.use("/listings", listingRoutes);

// Start the API server.
const PORT = 3000;

app.listen(PORT, async () => {
    console.log(`The Student Marketplace API is running on port ${PORT}.`);

    // Test the connection to the online MySQL database.
    try {
        await testDatabaseConnection();
    } catch (error) {
        console.error("Failed to connect to the Aiven MySQL database:", error);
    }
});