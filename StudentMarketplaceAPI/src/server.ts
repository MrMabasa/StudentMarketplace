import express from "express";
import cors from "cors";

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

// Start the API server.
const PORT = 3000;

app.listen(PORT, () => {
    console.log(`The Student Marketplace API is running on port ${PORT}.`);
});