package com.example.pilotapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pilotapp.ui.theme.AboutActivity
import com.example.pilotapp.ui.theme.PilotAppTheme
import com.example.pilotapp.ui.theme.QuestionsActivity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {

            }
            val username = intent.getStringExtra("Username")
            val actualScore = intent.getIntExtra("SCORE", 0)
            val home_icon = ImageVector.vectorResource(R.drawable.home_alt_1)
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
                        { it ->
                            if (it.id == "About") {
                                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Clicked on ${it.title}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                },
                bottomBar = {
                    val learnIcon = ImageVector.vectorResource(id = R.drawable.learn_icon)
                    val ArenaIcon = ImageVector.vectorResource(id = R.drawable.arenaicon)
                    val SocialIcon = ImageVector.vectorResource(id = R.drawable.socialicon)
                    val UserIcon = ImageVector.vectorResource(id = R.drawable.user_icon)
                    val tradeicon = ImageVector.vectorResource(id = R.drawable.tradeicon)
                    BottomNavBar(
                        items =
                        listOf(
                            BottomNavitem(
                                name = "Learn",
                                route = "Learn",
                                icon = learnIcon
                            ), BottomNavitem(
                                name = "Arena",
                                route = "Arena",
                                icon = ArenaIcon
                            ), BottomNavitem(
                                name = "Trade",
                                route = "Trade",
                                icon = tradeicon
                            ),
                            BottomNavitem(
                                name = "Social",
                                route = "Social",
                                icon = SocialIcon,
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
                Navigation(
                    navController = navController,
                    modifier = Modifier.padding(padding),
                    username,
                    actualScore
                )
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
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    tint = Color(0xff01ACAD)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                                tint = Color(0xff01ACAD)
                            )
                        }

                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }

                })
        }

    }
}

//TODO- THIS is the code of navigation between screens
@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    username: String?,
    Score: Int
) {
    NavHost(navController = navController, startDestination = "Learn")
    {

        composable("Learn")
        {
            LearnScreen(navController, username = username, Score)

        }
        composable("Arena")
        {
            ArenaScreen()
        }
        composable("Social") {
            SocialScreen(navController = navController)
        }
        composable("User")
        {
            UserAccountScreen(name = username, Score = Score, navController)
        }
        composable("Modules")
        {
            ModuleScreen(navController)
        }
        composable("VideoPlayer")
        {
            VideoPlayerScreen(navController)
        }
        composable("Goal")
        {
            GoalsScreen()
        }
        composable("Portfolio")
        {
            PortfolioScreen()
        }
        composable("Trade")
        {
            Tradescreen()
        }
        composable("Bot")
        {
            BotScreen()
        }
        composable("Forum")
        {
            Forumscreen(navController)
        }
        composable("News")
        {
            Newsscreen(navController)
        }
        composable("Community")
        {
            Communityscreen(navController)
        }
//        composable("Search")
//        {
//            SearchScreen()
//        }

    }
}

@Composable
fun Communityscreen(navController: NavController) {
    val commimage = painterResource(id = R.drawable.communityy)
    Box(
        Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Image(
            painter = commimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun Newsscreen(navController: NavController) {
    val newsimage = painterResource(id = R.drawable.newss)
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = newsimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f)
                .padding(5.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun Forumscreen(
    navController: NavController
) {
    val forum = painterResource(id = R.drawable.forum01)
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = forum,
            contentDescription = null,
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun BotScreen() {
    val botimage = painterResource(id = R.drawable.chatbotpage)
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = botimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f)
                ,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun Tradescreen() {
    val tradeimage = painterResource(id = R.drawable.tradeimagee)
    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Image(
            painter = tradeimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun PortfolioScreen() {
    val portfolioimage = painterResource(id = R.drawable.portfolioimage)
    Box(
        Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Image(
            painter = portfolioimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun GoalsScreen() {
    val goalsimage = painterResource(id = R.drawable.goalimage)
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = goalsimage,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f)
                .padding(5.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun VideoPlayerScreen(navcontroller: NavController) {
    var count by remember {
        mutableStateOf(0)
    }
    val rect02 = painterResource(id = R.drawable.rectangle15)
    val rect03 = painterResource(id = R.drawable.rectangle20)
    val videoplayerbutton = ImageVector.vectorResource(id = R.drawable.videoplayerbutton02)
    Column(Modifier.padding(25.dp)) {

        Box() {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(
                    text = "← Previous",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color(0xff6c757d),
                    )
                )
                Text(
                    text = if (count < 2) "Next →" else if (count == 2) "Take assessment" else "Module Screen",
                    Modifier.clickable {
                        if (count < 3) {
                            count++
                        } else {
                            navcontroller.navigate("Modules")
                        }
                    },
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color(0xff6c757d),
                    )

                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Beginner",
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            )
        )
        Spacer(
            Modifier.height(5.dp)
        )
        Card(
            Modifier
                .height(200.dp)
                .width(400.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color(0xffD9D9D9)
        )
        {
            Box(Modifier.size(50.dp), contentAlignment = Center)
            {
                Icon(
                    imageVector = videoplayerbutton,
                    contentDescription = null,
                    Modifier.size(50.dp),
                    tint = Color(0xff01ACAD)
                )
            }
            Spacer(Modifier.height(5.dp))
        }
        Dropdownmenu(count)
    }
}


@Composable
fun ModuleScreen(navController: NavController) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("VideoPlayer")
                })
        {
            Text("Module 1 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Understanding Financial Markets",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 2 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                "Investment Fundamentals",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 3 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " " +
                        "Getting Started with Trading",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 4 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Investment Terminologies",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 5 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Investment Strategies",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 6 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Risk Management",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 7 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Investment Tools and Resources",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 8 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Case Studies and Practical Examples",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 14.sp
            )
        }
        Spacer(Modifier.height(5.dp))
        drawlinecandle()
        Spacer(Modifier.height(15.dp))
        Row(Modifier.fillMaxWidth())
        {
            Text("Module 9 |", fontWeight = FontWeight(500), fontSize = 20.sp)
            Spacer(Modifier.width(15.dp))
            Text(
                " Next Steps in Investing",
                Modifier.padding(vertical = 3.dp),
                fontWeight = FontWeight(400),
                fontSize = 15.sp
            )
        }
        Spacer(Modifier.height(10.dp))
        drawlinecandle()
    }
}


@Composable
fun SocialScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp)
    )
    {
        Row(Modifier.fillMaxWidth())
        {
            Spacer(modifier = Modifier.width(50.dp))
            Button(
                onClick = { },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff50BEBF))
            ) {
                Text(
                    text = "Forum",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { navController.navigate("News") },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffFFFFFF))
            ) {
                Text(
                    text = "News",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { navController.navigate("Community") },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color(0xffFFFFFF))
            ) {
                Text(
                    text = "Community",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier
                .fillMaxSize()
        ) {
            val forumm = painterResource(id = R.drawable.forum01)
            Image(
                painter = forumm,
                contentDescription = null,
                Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(navController: NavController, username: String?, score: Int) {
    val videoimage = ImageVector.vectorResource(id = R.drawable.videoseriespilotimage)
    val chatbotimage = painterResource(id = R.drawable.chatbotimage)

    //TODO- setting the context for opening the second activity
    // this is how its done in jetpack compose

    val Cur_context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Hello User!", fontSize = 22.sp, fontWeight = FontWeight(500))
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .padding(10.dp)
                .height(150.dp)
                .width(300.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    //Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    )
                    {
                        Image(
                            imageVector = videoimage,
                            contentDescription = null,
                            Modifier.size(110.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(5.dp))
                        Column() {
                            Text(
                                "Pilot video series",
                                fontSize = 15.sp,
                                fontWeight = FontWeight(400),
                                color = Color.Black
                            )
                            Spacer(Modifier.height(3.dp))
                            Text(
                                "Introducing pilot video series for absolute beginner. we picked essential topics for you to get started.",
                                fontSize = 15.sp,
                                fontWeight = FontWeight(300),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Continue reading",
            Modifier.padding(10.dp),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            )
        )
        Spacer(Modifier.height(10.dp))
        Box()
        {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                CourseCardBody(
                    CourseCard("Stock market basics", "Beginner", 0.5, 1),
                    navController
                )
                CourseCardBody(
                    CourseCard("Advance market analysis", "Intermediate", 0.2, 2),
                    navController
                )
                CourseCardBody(
                    CourseCard("Global investment Strategy", "Advanced", 0.1, 3),
                    navController
                )
                Spacefixer(count = 10)
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(Modifier.height(60.dp))
                Row()
                {
                    Spacer(Modifier.width(310.dp))
                    Image(
                        painter = chatbotimage,
                        contentDescription = null,
                        Modifier
                            .size(70.dp)
                            .clickable {
                                navController.navigate("Bot")
                            },
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun ArenaScreen() {
    val arenascreen = painterResource(id = R.drawable.androidsmallarenaaa)
    Box(Modifier.fillMaxSize())
    {
        Image(
            painter = arenascreen,
            contentDescription = null,
            Modifier
                .fillMaxSize(0.9f)
                .padding(5.dp),
            contentScale = ContentScale.Fit
        )
    }
//    val firstfront = ImageVector.vectorResource(id = R.drawable.firstfrontstockssellchart)
//    val firstback = ImageVector.vectorResource(id = R.drawable.firstbackwhitecircle)
//    val middlefront = ImageVector.vectorResource(id = R.drawable.middlefrontmoneygrowth)
//    val middleback = ImageVector.vectorResource(id = R.drawable.middlebackgreencircle)
//    val lastfront = ImageVector.vectorResource(id = R.drawable.lastfrontstockmarketanalysis)
//    val lastback = ImageVector.vectorResource(id = R.drawable.lastbackyellowcircle)
//    val bull=ImageVector.vectorResource(id = R.drawable.bullmarket_1)
//    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(15.dp)
//    )
//    {
//        Spacer(Modifier.height(15.dp))
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
//        {
//            Box(Modifier.size(70.dp), contentAlignment = Center)
//            {
//                Image(
//                    imageVector = middleback,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//                Image(
//                    imageVector = middlefront,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(0.7f),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//            }
//            Box(Modifier.size(70.dp), contentAlignment = Center)
//            {
//                Image(
//                    imageVector = firstback,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//                Image(
//                    imageVector = firstfront,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(0.7f),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//            }
//            Box(Modifier.size(70.dp), contentAlignment = Center)
//            {
//                Image(
//                    imageVector = lastback,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//                Image(
//                    imageVector = lastfront,
//                    contentDescription = null,
//                    Modifier.fillMaxSize(0.7f),
//                    contentScale = ContentScale.FillBounds,
//                    alignment = Center
//                )
//            }
//        }
//        Spacer(Modifier.height(30.dp))
//        Text(
//            text = "Open Leagues",
//            Modifier.padding(horizontal = 10.dp),
//            style = TextStyle(
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF000000),
//            )
//        )
//        Spacer(Modifier.height(10.dp))
//        Row()
//        {
//
//            Text(
//                text = "Sorted by:",
//                Modifier.padding(horizontal = 10.dp),
//                style = TextStyle(
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.SemiBold
//                ),
//                color = Color(0xFF000000),
//            )
//            Spacer(modifier = Modifier.width(3.dp))
//            Text(
//                text = "Start Time",
//                Modifier.padding(vertical = 2.dp),
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Light
//                ),
//                color = Color(0xFF000000),
//            )
//        }
//        Spacer(Modifier.height(10.dp))
//        Card(
//            Modifier
//                .fillMaxWidth()
//                .height(200.dp))
//        {
//            Column(modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)) {
//
//                    Row() {
//                        Image(imageVector = bull, contentDescription = null, contentScale = ContentScale.Fit)
//
//                        Spacer(modifier = Modifier.width(10.dp))
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                        ) {
//                            Text(
//                                text = "Monday trident", fontWeight = FontWeight.Bold,
//                                color = Color(0xFF000000),
//                                textAlign = TextAlign.Center,
//                                fontSize = 25.sp
//                            )
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text(
//                                text = , color = Color(0xFF4f5d75),
//                                textAlign = TextAlign.Center
//                            )
//
//
//                            Spacer(modifier = Modifier.height(5.dp))
//
//                            Text(
//                                text = "Within ${it.range} KM",
//                                color = Color(0xFF000000),
//                                textAlign = TextAlign.Center
//                            )
//
//                            Spacer(modifier = Modifier.height(5.dp))
//
//                            LinearProgressIndicator(
//                                progress = 0.4f,
//                                modifier = Modifier
//                                    .width(130.dp)
//                                    .height(10.dp),
//                                color = Color(0xFF023047),
//                                trackColor = Color(0xFFC4C4C4)
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(7.dp))
//                    Column(
//                        modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp),
//                        horizontalAlignment = Alignment.Start
//                    )
//                    {
//
//                        Text(
//                            text = "Coffee|Business|Friendship",
//                            color = Color(0xFF000000), fontSize = 15.sp
//                        )
//
//                        Spacer(Modifier.height(6.dp))
//                        Text(
//                            text = "Hi community i am open to work and make new connections",
//                            color = Color(0xFF343a40), fontSize = 13.sp
//                        )
//
//                    }
//                }
//            }
//        }
}


@Composable
fun UserAccountScreen(name: String?, Score: Int, navController: NavController) {
    val moneybag = ImageVector.vectorResource(id = R.drawable.moneybagalt)
    val wallet = ImageVector.vectorResource(id = R.drawable.wallet)
    val book = ImageVector.vectorResource(id = R.drawable.bookicon)
    val bag = ImageVector.vectorResource(id = R.drawable.portfolioicon)
    val logouticon = ImageVector.vectorResource(id = R.drawable.logout_icon)
    val context = LocalContext.current
    val painter = painterResource(id = R.drawable.user_profile)
    val painter02 = painterResource(id = R.drawable.progressellipse)
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Spacer(modifier = Modifier.width(12.dp))
            Image(painter, null, Modifier.size(90.dp), contentScale = ContentScale.FillBounds)
            Spacer(Modifier.width(20.dp))
            Column {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = name.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 25.sp
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "abcd@gmail.com",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF6D6D6D),
                    )
                )
            }
        }
        Text(
            text = "Course completion",
            Modifier.padding(horizontal = 95.dp),
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000),
            )
        )
        Spacer(Modifier.height(7.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Box(Modifier.size(150.dp), contentAlignment = Center)
            {
                Image(
                    painter = painter02,
                    contentDescription = null,
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "25%",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1F884B),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        Spacer(Modifier.height(7.dp))
        Text(
            text = if (Score in 0..2) "Beginner" else "Advanced",
            Modifier.padding(horizontal = 145.dp),
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000),
            )
        )
        Spacer(Modifier.height(20.dp))
        Card(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp), elevation = 5.dp, shape = RoundedCornerShape(13.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxSize())
                    {
                        Spacer(Modifier.width(5.dp))
                        Image(
                            imageVector = book,
                            contentDescription = null,
                            Modifier.size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Course recommendation",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    navController.navigate("Goal")
                }, elevation = 5.dp, shape = RoundedCornerShape(13.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxSize())
                    {
                        Spacer(Modifier.width(5.dp))
                        Image(
                            imageVector = moneybag,
                            contentDescription = null,
                            Modifier.size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "My goal",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp), elevation = 5.dp, shape = RoundedCornerShape(13.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxSize())
                    {
                        Spacer(Modifier.width(5.dp))
                        Image(
                            imageVector = wallet,
                            contentDescription = null,
                            Modifier.size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "My wallet",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    navController.navigate("Portfolio")
                }, elevation = 5.dp, shape = RoundedCornerShape(13.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxSize())
                    {
                        Spacer(Modifier.width(5.dp))
                        Image(
                            imageVector = bag,
                            contentDescription = null,
                            Modifier.size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Portfolio",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }, elevation = 5.dp, shape = RoundedCornerShape(13.dp)
        )
        {
            Box(Modifier.fillMaxSize(), contentAlignment = Center)
            {
                Column() {
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxSize())
                    {
                        Spacer(Modifier.width(5.dp))
                        Image(
                            imageVector = logouticon,
                            contentDescription = null,
                            Modifier.size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Log out",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}


//@Composable
//fun SearchScreen() {
//    val viewModel = viewModel<MainViewModel>()
//    val searchText by viewModel.searchText.collectAsState()
//    val persons by viewModel.persons.collectAsState()
//    val isSearching by viewModel.isSearching.collectAsState()
//    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Row(
//            Modifier
//                .fillMaxWidth()
//        )
//        {
//            val filter_icon = ImageVector.vectorResource(id = R.drawable.filter)
//            val Search_icon = ImageVector.vectorResource(id = R.drawable.searchicon)
//            Icon(
//                imageVector = filter_icon, contentDescription = null,
//                Modifier
//                    .padding(3.dp)
//                    .padding(vertical = 10.dp)
//            )
//            OutlinedTextField(
//                value = searchText,
//                onValueChange = viewModel::onSearchChange,
//                Modifier
//                    .padding(3.dp)
//                    .fillMaxWidth(),
//                textStyle = TextStyle(
//                    fontSize = 17.sp,
//                    //lineHeight =,
//                ),
//                trailingIcon = {
//                    Icon(
//                        imageVector = Search_icon,
//                        contentDescription = null,
//                        Modifier.size(20.dp)
//                    )
//                },
//                placeholder = { Text(text = "Search Courses") },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    cursorColor = Color(
//                        0xff80ed99
//                    )
//                )
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        if (isSearching) {
//            Box(Modifier.fillMaxSize()) {
//                CircularProgressIndicator(Modifier.align(Alignment.Center))
//            }
//        } else {
//            LazyColumn(Modifier.fillMaxWidth())
//            {
//                items(persons)
//                { person ->
//                    Text(
//                        text = "${person.firstname} ${person.lastname}",
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp)
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun SearchbarRow(navController: NavController) {
//    val searchbox = painterResource(id = R.drawable.rectangle15)
//    val Search_icon = ImageVector.vectorResource(id = R.drawable.searchicon)
//    val userProfile = ImageVector.vectorResource(id = R.drawable.user_profile)
//    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center)
//    {
//        Spacer(modifier = Modifier.width(30.dp))
//        Card(
//            Modifier
//                .fillMaxWidth(0.8f)
//                .padding(vertical = 20.dp)
//                .clickable {
//                    navController.navigate("Search")
//                },
//            shape = RoundedCornerShape(10.dp),
//            elevation = 5.dp
//        )
//        {
//            Box(
//                Modifier
//                    .height(35.dp), contentAlignment = Center
//            )
//            {
//                Image(painter = searchbox, contentDescription = null)
//                Row() {
//                    Spacer(modifier = Modifier.width(130.dp))
//                    Text(text = "Find Courses", color = Color(0xff01ACAD), fontSize = 20.sp)
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Icon(
//                        imageVector = Search_icon, contentDescription = null,
//                        Modifier
//                            .size(33.dp)
//                            .padding(vertical = 6.dp)
//                    )
//                }
//            }
//        }
//        Spacer(modifier = Modifier.width(14.dp))
//        Box(
//            Modifier
//                .padding(vertical = 20.dp)
//                .fillMaxSize()
//        )
//        {
//            Icon(
//                imageVector = userProfile,
//                contentDescription = null,
//                Modifier
//                    .size(35.dp)
//                    .clickable {
//                        navController.navigate("User")
//                    })
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCardBody(
    item: CourseCard,
    navController: NavController
) {
    Box(
        Modifier
            .fillMaxSize()
            .clickable {
                if (item.number == 1) {
                    navController.navigate("Modules")
                }
            }, contentAlignment = Center
    ) {
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(horizontal = 5.dp)
                .width(320.dp)
                .height(110.dp), elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        )
        {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
            {
                Text(
                    text = "Module :",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF6D6D6D),
                    )
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "${item.number} | ${item.courseName}",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "Level : ${item.level}",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF6D6D6D),
                    )
                )
                Spacer(Modifier.height(5.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = Color.Green,
                    progress = item.progress.toFloat(),
                )
            }
        }
    }
}


@Composable
fun Spacefixer(count: Int) {
    repeat(count) { counter ->
        Text(" ")
    }
}

@Composable
fun drawlinecandle() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    {
        drawLine(
            color = Color.Black,
            start = Offset(30f, 0f),
            end = Offset(1000f, 0f)
        )
    }
}

@Composable
fun Dropdownmenu(total: Int) {
    var smallcircle = ImageVector.vectorResource(id = R.drawable.checkcircle)

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf("")
    }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
        )
        {
            Text(
                text = "Stock market basics",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF000000),
                )
            )
            Spacer(Modifier.width(4.dp))
            Icon(imageVector = icon, contentDescription = null, Modifier.clickable {
                expanded = !expanded
            })
        }
        Spacer(Modifier.height(3.dp))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            Modifier.background(Color.White)
        )
        {
            Card(
                Modifier
                    .width(350.dp)
                    .height(150.dp), elevation = 5.dp, shape = RoundedCornerShape(1.dp)
            )
            {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Spacer(Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth())
                    {
                        Icon(
                            imageVector = smallcircle,
                            contentDescription = null,
                            Modifier.size(15.dp),
                            tint = if (total > 0) Color(0xff5BD29D) else Color(0xff6D6D6D)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Lesson 1.1: What Are Financial Markets?",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Light,
                                color = if (total > 0) Color(0xFF5BD29D) else Color(0x80000000)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    drawlinecandle()
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth())
                    {
                        Icon(
                            imageVector = smallcircle,
                            contentDescription = null,
                            Modifier.size(15.dp),
                            tint = if (total > 1) Color(0xff5BD29D) else Color(0xff6D6D6D)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Lesson 1.2: Types of Assets (Stocks, Bonds, Commodities)",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Light,
                                color = if (total > 1) Color(0xFF5BD29D) else Color(0x80000000),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    drawlinecandle()
                    Row(Modifier.fillMaxWidth())
                    {
                        Icon(
                            imageVector = smallcircle,
                            contentDescription = null,
                            Modifier.size(15.dp),
                            tint = if (total > 2) Color(0xff5BD29D) else Color(0xff6D6D6D)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Lesson 1.3: Role of Exchanges and Brokers",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Light,
                                color = if (total > 2) Color(0xFF5BD29D) else Color(0x80000000),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    drawlinecandle()
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = smallcircle,
                            contentDescription = null,
                            Modifier.size(15.dp),
                            tint = if (total > 3) Color(0xff5BD29D) else Color(0xff6D6D6D)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "Assessment",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Light,
                                color = if (total > 3) Color(0xFF5BD29D) else Color(0x80000000),
                            )
                        )
                    }
                }
            }
        }
    }
}










