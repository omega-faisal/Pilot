package com.example.pilotapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilotapp.R
import com.example.pilotapp.ui.theme.ui.theme.PilotAppTheme

class CandlestickActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                val pilotlogo_removed = painterResource(id = R.drawable.pilothead_removed)
                //val state = rememberScaffoldState()

                Column(Modifier.fillMaxSize()) {
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
                                text = "Candlesticks",
                                fontSize = 21.sp
                            )

                        },
                        backgroundColor = Color(0xff01ACAD),
                        contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary,

                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    val bullishing = painterResource(id = R.drawable.bullishingulphing)
                    val morningstar = painterResource(id = R.drawable.morningstar)

                    //of bearish
                    val bearishing = painterResource(id = R.drawable.bearishingulphing)
                    val shootingstar = painterResource(id = R.drawable.shootingstar)
                    val hangingman = painterResource(id = R.drawable.hangingman)
                    Candles(
                        items = listOf(
                            ChartTypes(
                                bullishing,
                                "Bullish Engulfing",
                                "The bullish engulfing pattern is a popular candlestick pattern in technical analysis that is often considered a reversal pattern. It occurs during a downtrend and suggests a potential change in market direction from bearish (downward) to bullish (upward)"
                            ),
                            ChartTypes(
                                morningstar,
                                "Morning Star",
                                "The morning star pattern is a bullish candlestick pattern. It forms after a downtrend and suggests a potential reversal. It consists of three candles: a long bearish candle, a small indecisive candle, and a long bullish candle. It indicates a shift from bearish to bullish sentiment and potential upward price movement."
                            ),
                            ChartTypes(
                                bearishing,
                                "Bearish Engulfing",
                                "The bearish engulfing pattern is a candlestick pattern in technical analysis. It signals a potential reversal in an uptrend. It consists of two candles: the first is bullish (upward), the second is bearish (downward), and it completely engulfs the first candle's body. This suggests a shift from bullish to bearish sentiment."
                            ),
                            ChartTypes(
                                shootingstar,
                                "Shooting Star",
                                "The shooting star pattern is a bearish candlestick pattern. It forms after an uptrend and indicates a potential reversal. It has a small body located at the bottom and a long upper wick, resembling an inverted 'T'. It suggests buyer exhaustion and possible price decline."
                            ),
                            ChartTypes(
                                hangingman,
                                "Hanging Man",
                                "The hanging man pattern is a bearish candlestick pattern. It develops after an uptrend, signaling a possible reversal. It has a small body near the top and a long lower wick, resembling a hanging man,It suggests potential selling pressure and a shift towards a price drop.",
                                true
                            )
                        )
                    )
                }
            }
        }

    }
}


@Composable
fun Candles(
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
                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Spacer(Modifier.height(60.dp))
                    Image(
                        painter = chart.picture,
                        contentDescription = null,
                        Modifier
                            .size(80.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(Modifier.width(2.dp))
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
                drawlinecandle()
            }
        }
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
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f)
        )
    }
}