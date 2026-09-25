# The Student Marketplace

The Student Marketplace is an Android marketplace application designed for students to buy and sell items within the student community.

This project is being developed for the **OPSC6312 Open Source Coding Portfolio of Evidence (POE)**. The Part 2 prototype focuses on authentication, marketplace listings, REST API/database integration, settings, and core student marketplace features.

## Current Part 2 Prototype

- Firebase email/password registration and sign-in
- Marketplace listings and search
- Create listing with title, description, price, category, condition and optional module code
- Listing photo selection
- REST API integration
- Aiven Cloud MySQL database
- Buyer purchase requests
- Accept/decline buyer requests
- Favourites
- Profile and Settings
- Persistent settings using Android DataStore
- Notification preference settings
- Default category preference storage
- Remember-search/filter preference storage
- Dark Mode integration in progress
- Git/GitHub version history

## Technology Stack

### Android
- Kotlin
- Jetpack Compose
- Material 3
- Navigation
- ViewModel
- Retrofit
- OkHttp
- DataStore Preferences
- Firebase Authentication

### REST API
- Node.js
- Express
- TypeScript
- mysql2
- CORS

### Database
- MySQL
- Aiven Cloud MySQL

## Architecture

```text
Android UI
    |
    v
ViewModel
    |
    v
Retrofit + OkHttp
    |
    v
Node.js + Express REST API
    |
    v
Aiven MySQL
```

Firebase Authentication provides the current email/password authentication flow.

## REST API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/listings` | Retrieve listings |
| GET | `/listings/:id` | Retrieve one listing |
| POST | `/listings` | Create a listing |
| PUT | `/listings/:id` | Update a listing |
| DELETE | `/listings/:id` | Delete a listing |

## Core Features

### Authentication
Users can register, sign in and sign out.

### Marketplace Listings
Users can browse and search listings, open listing details, select a photo, enter listing information and submit a listing through the REST API.

### Buyer Requests
A buyer can request to purchase a listing. The seller can view, accept or decline the request.

### Favourites
Users can save listings as favourites and view saved favourites.

### Settings
The Settings screen includes:
- Dark Mode
- Language selection
- Marketplace notifications
- Buyer request notifications
- Favourite/listing notifications
- Default category
- Remember search/filter settings
- Sign out
- Application information

Settings are stored using Android DataStore.

## Running the REST API Locally

From the `StudentMarketplaceAPI` directory:

```powershell
npx tsx src/server.ts
```

The development API runs on port `3000` and connects to the online Aiven MySQL database.

Do not commit `.env` or database credentials to GitHub.

## Database

The listing data includes:
- Listing ID
- Seller ID
- Title
- Description
- Price
- Condition
- Category
- Module code
- Status
- Creation date

Database changes are maintained using SQL migrations.

## Development Workflow

```text
Implement feature
      ↓
Build
      ↓
Test on emulator
      ↓
Test API where applicable
      ↓
Git commit
      ↓
Push to GitHub
```

## Part 2 Status

| Criterion | Current status |
|---|---|
| App runs on mobile | Working on Android emulator; physical-device evidence still required |
| Sign in | Implemented and tested |
| Settings | Implemented; some settings still need behaviour integration |
| Creation of REST API | Implemented |
| Integration of REST API | Implemented and tested locally |
| User Defined Feature 1: Marketplace Listings | Implemented |
| User Defined Feature 2: Buyer Requests | Implemented as working prototype |
| User Defined Feature 3: Favourites | Implemented as working prototype |
| UI | Implemented |
| GitHub / README | Git history and repository present; README included |
| Automated testing / GitHub Actions | Still required |
| Demo video | Still required |

### Important Part 2 limitation

The REST API is currently demonstrated through the local development server while using the online Aiven MySQL database. A publicly hosted API endpoint still needs to be completed for a fully online deployment demonstration.

## Planned Part 3

- Google SSO
- Offline mode with Room
- Synchronisation when online
- Firebase Cloud Messaging push notifications
- English, Xitsonga and Setswana support
- Moderation/reporting
- Module-code textbook matching
- Release preparation
- Play Store preparation
- Automated testing and CI improvements

## Academic Project

**Module:** OPSC6312 – Open Source Coding  
**Project:** The Student Marketplace  
**Stage:** Part 2 Working Prototype
