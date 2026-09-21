package com.studentmarketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studentmarketplace.ui.theme.StudentMarketplaceTheme

data class Listing(
    val id: Int,
    val title: String,
    val moduleCode: String? = null,
    val price: String,
    val condition: String,
    val category: String,
    val description: String,
    val imageRes: Int? = null
)

val listings = mutableStateListOf(

    Listing(
        id = 1,
        title = "Principles of Information Security",
        moduleCode = "ISEC6321",
        price = "R500",
        condition = "Very Good",
        category = "Textbooks and Study Material",
        description = "Seventh edition textbook for IT 2nd year students.",
        imageRes = R.drawable.principles_information_security
    ),

    Listing(
        id = 2,
        title = "Successful Project Management",
        moduleCode = "IPMA6212",
        price = "R680",
        condition = "Good",
        category = "Textbooks and Study Material",
        description = "Seventh edition textbook for project management students.",
        imageRes = R.drawable.successful_project_management
    ),

    Listing(
        id = 3,
        title = "Database Systems",
        moduleCode = "DATA6112",
        price = "R350",
        condition = "Good",
        category = "Textbooks and Study Material",
        description = "Second edition textbook for database systems students.",
        imageRes = R.drawable.database_systems
    ),

    Listing(
        id = 4,
        title = "Systems Analysis and Design",
        moduleCode = "SAND6221",
        price = "R450",
        condition = "Good",
        category = "Textbooks and Study Material",
        description = "Twelfth edition textbook for systems analysis and design.",
        imageRes = R.drawable.systems_analysis_design
    ),

    Listing(
        id = 5,
        title = "Acer 31.5-inch LED Gaming Monitor",
        price = "R2350",
        condition = "Like New",
        category = "Electronics and Accessories",
        description = "31.5-inch Acer LED gaming monitor suitable for studying, gaming and general computer use.",
        imageRes = R.drawable.acer_monitor
    ),

    Listing(
        id = 6,
        title = "A1 Drawing Board with Stand",
        price = "R320",
        condition = "Good",
        category = "Stationery and Course Equipment",
        description = "A1 drawing board with stand for students who need a larger workspace for technical drawing and design work.",
        imageRes = R.drawable.a1_drawing_board
    ),

    Listing(
        id = 7,
        title = "TCL 21-inch Compact Single-Door Mini Fridge",
        price = "R1200",
        condition = "Very Good",
        category = "Dorm and Daily Essentials",
        description = "Compact TCL mini fridge suitable for a student residence or bedroom.",
        imageRes = R.drawable.tcl_mini_fridge
    ),

    Listing(
        id = 8,
        title = "Single Bed Base",
        price = "R500",
        condition = "Very Good",
        category = "Other Student Items",
        description = "Single bed base in very good condition, suitable for a student residence.",
        imageRes = R.drawable.single_bed_base
    )
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentMarketplaceTheme {
                StudentMarketplaceApp()
            }
        }
    }
}

@Composable
fun StudentMarketplaceApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("browse") {
            BrowseListingsScreen(navController)
        }

        composable("createListing") {
            CreateListingScreen(navController)
        }

        composable("listing/{listingId}") { backStackEntry ->

            val listingId =
                backStackEntry.arguments
                    ?.getString("listingId")
                    ?.toIntOrNull()

            val listing =
                listings.firstOrNull {
                    it.id == listingId
                }

            if (listing != null) {
                ListingDetailsScreen(
                    navController = navController,
                    listing = listing
                )
            }
        }

        composable("profile") {
            ProfileScreen(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedRoute = "home"
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "The Student Marketplace",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Buy and sell items within the student community.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Button(
                onClick = {
                    navController.navigate("browse")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Browse Listings")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    navController.navigate("createListing")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Listing")
            }
        }
    }
}

@Composable
fun CreateListingScreen(navController: NavController) {

    var title by remember {
        mutableStateOf("")
    }

    var moduleCode by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var condition by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val categories = listOf(
        "Textbooks and Study Material",
        "Electronics and Accessories",
        "Stationery and Course Equipment",
        "Dorm and Daily Essentials",
        "Other Student Items"
    )

    val conditions = listOf(
        "New",
        "Like New",
        "Very Good",
        "Good",
        "Fair"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(20.dp)
    ) {

        item {

            Text(
                text = "Create Listing",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Add an item that you would like to sell.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Title")
                },
                placeholder = {
                    Text("e.g. Calculus Textbook")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = moduleCode,
                onValueChange = {
                    moduleCode = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Module Code (Optional)")
                },
                placeholder = {
                    Text("e.g. MATH6112")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Description")
                },
                placeholder = {
                    Text("Describe the item...")
                },
                minLines = 4
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Price")
                },
                placeholder = {
                    Text("e.g. 500")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Category",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            categories.forEach { item ->

                Button(
                    onClick = {
                        category = item
                        errorMessage = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = if (category == item) {
                            "✓ $item"
                        } else {
                            item
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Condition",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            conditions.forEach { item ->

                Button(
                    onClick = {
                        condition = item
                        errorMessage = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = if (condition == item) {
                            "✓ $item"
                        } else {
                            item
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            Button(
                onClick = {

                    when {
                        title.isBlank() -> {
                            errorMessage = "Please enter a listing title."
                        }

                        description.isBlank() -> {
                            errorMessage = "Please enter a description."
                        }

                        price.isBlank() -> {
                            errorMessage = "Please enter a price."
                        }

                        price.toDoubleOrNull() == null -> {
                            errorMessage = "Please enter a valid price."
                        }

                        category.isBlank() -> {
                            errorMessage = "Please select a category."
                        }

                        condition.isBlank() -> {
                            errorMessage = "Please select a condition."
                        }

                        else -> {

                            val newId =
                                (listings.maxOfOrNull { it.id } ?: 0) + 1

                            listings.add(
                                Listing(
                                    id = newId,
                                    title = title.trim(),
                                    moduleCode = moduleCode
                                        .trim()
                                        .ifBlank { null },
                                    price = "R${price.trim()}",
                                    condition = condition,
                                    category = category,
                                    description = description.trim(),
                                    imageRes = null
                                )
                            )

                            navController.navigate("browse") {
                                popUpTo("createListing") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Listing")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Composable
fun BrowseListingsScreen(navController: NavController) {

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredListings = listings.filter { listing ->

        val query = searchText.trim()

        if (query.isEmpty()) {
            true
        } else {

            listing.title.contains(
                query,
                ignoreCase = true
            ) ||

                    listing.description.contains(
                        query,
                        ignoreCase = true
                    ) ||

                    listing.moduleCode?.contains(
                        query,
                        ignoreCase = true
                    ) == true
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedRoute = "browse"
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text(
                text = "Browse Listings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                placeholder = {
                    Text("Search listings...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {

                    if (searchText.isNotEmpty()) {

                        IconButton(
                            onClick = {
                                searchText = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (filteredListings.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No listings found.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = filteredListings,
                        key = { it.id }
                    ) { listing ->

                        ListingCard(
                            listing = listing,
                            onClick = {
                                navController.navigate(
                                    "listing/${listing.id}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListingCard(
    listing: Listing,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            if (listing.imageRes != null) {

                Image(
                    painter = painterResource(
                        id = listing.imageRes
                    ),
                    contentDescription = listing.title,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        ),
                    contentScale = ContentScale.Crop
                )

            } else {

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme
                                .secondaryContainer
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No Photo",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (listing.moduleCode != null) {

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = listing.moduleCode,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = listing.category,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = listing.price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Text(
                        text = listing.condition,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(
                                MaterialTheme
                                    .colorScheme
                                    .secondaryContainer,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun ListingDetailsScreen(
    navController: NavController,
    listing: Listing
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedRoute = "browse"
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(20.dp)
        ) {

            item {

                if (listing.imageRes != null) {

                    Image(
                        painter = painterResource(
                            id = listing.imageRes
                        ),
                        contentDescription = listing.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )

                } else {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme
                                    .secondaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text("No Photo")
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = listing.price,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                if (listing.moduleCode != null) {

                    Text(
                        text = "Module Code",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = listing.moduleCode,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }

                Text(
                    text = "Condition",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = listing.condition,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = listing.category,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = listing.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Listings")
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedRoute = "profile"
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Student Account",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Settings and account preferences will be added here.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedRoute: String
) {

    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
    ) {

        NavigationBarItem(
            selected = selectedRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            }
        )

        NavigationBarItem(
            selected = selectedRoute == "browse",
            onClick = {
                navController.navigate("browse") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Browse"
                )
            },
            label = {
                Text("Browse")
            }
        )

        NavigationBarItem(
            selected = selectedRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }
}