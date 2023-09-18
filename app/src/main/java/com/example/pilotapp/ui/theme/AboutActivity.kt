package com.example.pilotapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilotapp.ui.theme.ui.theme.PilotAppTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                Column(
                    Modifier.verticalScroll(rememberScrollState()).padding(20.dp)
                ) {
                    Text(
                        "About Pilot",

                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Welcome to PILOT, your trusted partner in the world of investments. " +
                                "We believe that everyone deserves the opportunity to make informed financial decisions," +
                                " regardless of their level of expertise. That's why we've created PILOT, a cutting-edge trading " +
                                "platform and AI-powered personalized investment guide.",

                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Our Mission",
                        Modifier.padding(horizontal = 4.dp),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,

                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "At PILOT, our mission is simple yet powerful: to democratize investing. We're on a mission" +
                                " to empower individuals like you to navigate the complexities of the financial markets " +
                                "with confidence. Whether you're a seasoned investor or just getting started, PILOT is here " +
                                "to guide you on your investment journey.",
                        Modifier.padding(horizontal = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Revolutionizing Investment",
                        Modifier.padding(horizontal = 4.dp),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "PILOT is not just another trading platform; it's a game-changer. We harness " +
                                "the power of artificial intelligence and machine learning to bring you a smarter," +
                                " more personalized investment experience. With PILOT, you're not alone in your " +
                                "investment decisions. Our AI-driven insights and guidance are here to help you make " +
                                "better choices.",
                        Modifier.padding(horizontal = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )

                }
            }
        }
    }
}

