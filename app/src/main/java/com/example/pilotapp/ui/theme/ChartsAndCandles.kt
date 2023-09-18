package com.example.pilotapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilotapp.R
import com.example.pilotapp.ui.theme.ui.theme.PilotAppTheme

class ChartsAndCandles : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                val pilotlogo_removed = painterResource(id = R.drawable.pilothead_removed)
                //val state = rememberScaffoldState()

                Scaffold(topBar =
                {
                    TopAppBar(
                        title = {
                            Column() {
                                Spacer(modifier = Modifier.height(9.dp))
                                Box(
                                    Modifier
                                        .height(80.dp)
                                        .width(70.dp),
                                )
                                {
                                    Image(
                                        painter = pilotlogo_removed,
                                        null,
                                        Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            androidx.compose.material.Text(
                                text = "Chart Patterns",
                                fontSize = 21.sp
                            )

                        },
                        backgroundColor = Color(0xff01ACAD),
                        contentColor = MaterialTheme.colors.onPrimary,

                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }) { padding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                    ) {
                        val triangle = painterResource(id = R.drawable.trianglepatternicon)
                        val flag = painterResource(id = R.drawable.realflag)
                        val hns = painterResource(id = R.drawable.headandshouldersicon)
                        val wedge = painterResource(id = R.drawable.wedgepattern)
                        Spacer(Modifier.height(60.dp))
                        Charts(
                            items = listOf(
                                ChartTypes(
                                    triangle,
                                    "Triangle Pattern",
                                    "The triangle pattern is considered to be a continuation" +
                                            " chart pattern which means that the prior trend will continue after the formation" +
                                            " of this chart pattern."
                                ),
                                ChartTypes(
                                    hns,
                                    "Head and Shoulders Pattern",
                                    "This chart pattern is very significant and important in predicting the trend, It is a specific chart formation that predicts a trend reversal."
                                ),
                                ChartTypes(
                                    flag,
                                    "Flag Pattern",
                                    "A flag pattern is a continuation pattern that shows candlesticks contained in a small parallelogram. It is an area of consolidation  which shows a counter-trend move that follows after a sharp price movement."
                                ),
                                ChartTypes(
                                    wedge,
                                    "Wedge Pattern",
                                    "A wedge pattern is a type of chart pattern that is formed by converging two trend lines. Wedges are the type of continuation as well as the reversal chart patterns.",
                                    true
                                ),

                                ), modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Charts(
    items: List<ChartTypes>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = Modifier.fillMaxSize())
    {
        items(items)
        { chart ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            {
                Column() {
                    Spacer(Modifier.height(30.dp))
                    Image(
                        painter = chart.picture,
                        contentDescription = null,
                        Modifier
                            .size(80.dp)
                            .padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Column() {
                    Text(
                        text = chart.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff01ACAD)
                    )
                    Spacer(modifier = Modifier.height(1.dp))

                    Text(text = chart.detail, fontWeight = FontWeight.Medium)
                }
            }
            if (!chart.islast) {
                drawline()
            }
        }
    }
}

@Composable
fun drawline() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    {
        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f)
        )
    }
}




