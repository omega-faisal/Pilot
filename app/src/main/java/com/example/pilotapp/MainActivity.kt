package com.example.pilotapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pilotapp.ui.theme.PilotAppTheme
import com.example.pilotapp.ui.theme.SearchActivity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                val home_icon = ImageVector.vectorResource(R.drawable.home_icon)
                val settings_icon = ImageVector.vectorResource(R.drawable.settings_icon)
                val about: ImageVector = ImageVector.vectorResource(id = R.drawable.iv_about)

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    },
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    icon = home_icon,
                                    description = null
                                ),
                                MenuItem(
                                    id = "Settings",
                                    title = "Settings",
                                    icon = settings_icon,
                                    description = null
                                ),
                                MenuItem(
                                    id = "About",
                                    title = "About",
                                    icon = about,
                                    description = null
                                )

                            ),
                            onItemClick =
                            {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Clicked on ${it.title}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    },
                    bottomBar = {
                        val learnIcon = ImageVector.vectorResource(id = R.drawable.learn_icon)
                        val ExploreIcon = ImageVector.vectorResource(id = R.drawable.explore_icon)
                        val DashIcon = ImageVector.vectorResource(id = R.drawable.home_alt_1)
                        val UserIcon = ImageVector.vectorResource(id = R.drawable.user_icon)
                        BottomNavBar(
                            items =
                            listOf(
                                BottomNavitem(
                                    name = "Dashboard",
                                    route = "Dashboard",
                                    icon = DashIcon
                                ),
                                BottomNavitem(
                                    name = "Learn",
                                    route = "Learn",
                                    icon = learnIcon
                                ),
                                BottomNavitem(
                                    name = "Explore",
                                    route = "Explore",
                                    icon = ExploreIcon,
                                    BadgeCount = 25
                                ),
                                BottomNavitem(
                                    name = "User",
                                    route = "User",
                                    icon = UserIcon,
                                )
                            ),

                            navController = navController,
                            onItemClick =
                            {
                                navController.navigate(it.route)
                            }
                        )
                    }
                )
                { padding ->
                    // this code is for removing the paddingError
                    Navigation(navController = navController, modifier = Modifier.padding(padding))
                }


            }
        }
    }

}

//TODO -this is the actual bottom navigation bar designer
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    items: List<BottomNavitem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavitem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        items.forEach { item ->

            val selected = item.route == backStackEntry.value?.destination?.route
            // this will check if the current route is same as the selected item's route

            BottomNavigationItem(selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {

                        if (item.BadgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge {
                                        Text(
                                            item.BadgeCount.toString(),
                                            color = Color(0xfff1faee),
                                            fontSize = 10.sp
                                        )
                                    }
                                },

                                ) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }

                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = Color(0xff80ed99)
                            )
                        }


                    }

                })
        }

    }
}

//TODO- THIS is the code of navigation between screens
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "Dashboard")
    {
        composable("Dashboard")
        {
            DashBoardScreen(modifier)
        }
        composable("Learn")
        {
            LearnScreen(navController)
        }
        composable("Explore")
        {
            ExploreScreen()
        }
        composable("User")
        {
            UserAccountScreen()
        }
        composable("Search")
        {
            SearchScreen()
        }

    }
}

@Composable
fun DashBoardScreen(modifier: Modifier = Modifier) {
    // TODO- we have to implement our home screen here
    Box(
        modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Dashboard")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(navController: NavController) {
    val bgImage = painterResource(id = R.drawable.backgroundpilot)
    val characterImage = painterResource(id = R.drawable.characterimage)

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = bgImage, contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        SearchbarRow(navController = navController)
        // this function given below covers all the code of search field

        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.width(15.dp))
                Column() {
                    Spacer(Modifier.height(130.dp))
                    Text(
                        "HI, User !",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Learn, Invest, and Earn with AI",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Column() {
                    Spacer(modifier = Modifier.height(130.dp))
                    Image(
                        painter = characterImage,
                        contentDescription = null,
                        Modifier
                            .size(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column() {
                val Course01 = painterResource(id = R.drawable.humbled_trader_education)
                val Course02= painterResource(id = R.drawable.stock_market)
                Text(
                    "Your Courses",
                    Modifier.padding(horizontal = 15.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Row()
                {
                    Spacer(Modifier.width(15.dp))
                    CourseCardBody(
                        items = listOf(
                            CourseCard("Stock market", 900, Course02, "good course", "2 h"),
                            CourseCard("Open market", 800, Course01, "good course", "1 h"),
                            CourseCard("Open market", 800, Course02, "good course", "1 h"),
                            CourseCard("Open market", 800, Course01, "good course", "1 h"),
                        )
                    )
                }
            }

        }
    }


}

@Composable
fun ExploreScreen() {
    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Explore")
    }
}

@Composable
fun UserAccountScreen() {
    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "User")
    }
}

@Composable
fun SearchScreen() {
    val viewModel = viewModel<MainViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        )
        {
            val filter_icon = ImageVector.vectorResource(id = R.drawable.filter)
            val Search_icon = ImageVector.vectorResource(id = R.drawable.searchicon)
            Icon(
                imageVector = filter_icon, contentDescription = null,
                Modifier
                    .padding(3.dp)
                    .padding(vertical = 10.dp)
            )
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchChange,
                Modifier
                    .padding(3.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 17.sp,
                    //lineHeight =,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Search_icon,
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )
                },
                placeholder = { Text(text = "Search Courses") },
                colors = TextFieldDefaults.outlinedTextFieldColors(cursorColor = Color(0xff80ed99))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(Modifier.fillMaxWidth())
            {
                items(persons)
                { person ->
                    Text(
                        text = "${person.firstname} ${person.lastname}",
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchbarRow(navController: NavController) {
    val searchbox = painterResource(id = R.drawable.rectangle15)
    val Search_icon = ImageVector.vectorResource(id = R.drawable.searchicon)
    val userProfile = ImageVector.vectorResource(id = R.drawable.user_profile)
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center)
    {
        Spacer(modifier = Modifier.width(30.dp))
        Card(
            Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 20.dp)
                .clickable {
                    navController.navigate("Search")
                },
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        )
        {
            Box(
                Modifier
                    .height(35.dp), contentAlignment = Center
            )
            {
                Image(painter = searchbox, contentDescription = null)
                Row() {
                    Spacer(modifier = Modifier.width(130.dp))
                    Text(text = "Find Courses", color = Color(0xff01ACAD), fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Search_icon, contentDescription = null,
                        Modifier
                            .size(33.dp)
                            .padding(vertical = 6.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Box(
            Modifier
                .padding(vertical = 20.dp)
                .fillMaxSize()
        )
        {
            Icon(
                imageVector = userProfile,
                contentDescription = null,
                Modifier
                    .size(35.dp)
                    .clickable {
                        navController.navigate("User")
                    })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCardBody(
    items: List<CourseCard>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier)
    {
        items(items)
        { coursecard ->
            Card(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 5.dp)
                    .height(90.dp)
                    .width(130.dp),
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            )
            {
                Box(Modifier.fillMaxSize())
                {
                    Image(
                        painter = coursecard.courseImage,
                        contentDescription = coursecard.description,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

}





