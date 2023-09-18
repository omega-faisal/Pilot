package com.example.pilotapp.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.pilotapp.MainActivity
import com.example.pilotapp.ui.theme.ui.theme.PilotAppTheme

class QuestionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                val username = intent.getStringExtra("USERNAME")
                QuizApp(afterFinish = { score ->
                    val intent = Intent(this@QuestionsActivity, MainActivity::class.java)
                    intent.putExtra("SCORE", score)
                    intent.putExtra("Username", username)
                    startActivity(intent)
                    finish()
                })

            }

        }
    }
}

@Composable
fun QuizApp(afterFinish: (Int) -> Unit) {
    val questionsreal = listOf(
        questions(
            "What is your primary investment goal?",
            listOf(
                "Saving for retirement",
                "buying a home",
                "funding education",
                "wealth accumulation"
            ),
        ),
        questions(
            "What is your preferred investment horizon?",
            listOf(
                "Short-term (1-3 years)",
                "medium-term (3-10 years)",
                "long-term (10+ years)"
            ),
        ),
        questions(
            "How comfortable are you with taking risks in your investments?",
            listOf(
                "Very risk-averse",
                "somewhat risk-averse",
                "neutral",
                "very risk-tolerant"
            ),
        ),
        questions(
            "What is your current level of investing knowledge?",
            listOf(
                "Novice",
                "Beginner",
                "Intermediate",
                "advanced"
            ),
        ),
        questions(
            "How much capital are you willing to invest initially",
            listOf(

                "1000",
                "5000",
                "10000",
                "Custom amount"
            ),
        ),
        questions(
            "Are you open to learning and actively managing your investments",
            listOf(

                "Yes, I want to learn and manage my investments actively.",
                "No, I prefer a more hands-off approach."

            ),
        ),questions(
            "Have you ever invested in stocks or other assets before?",
            listOf(

                "Yes, I have experience.",
               "No, I'm completely new to investing."

            ),
        ),questions(
            " Are there any specific industries or sectors you are interested in",
            listOf(

                "technology",
                "healthcare",
                "energy"
            ),
        )
        ,questions(
            " Do you have any specific ethical or sustainable investing preferences?",
            listOf(
                "Yes, I prefer to invest ethically or sustainably.",
                "No, it doesn't matter to me."
            ),
        )
        ,questions(
            ". What is your target annual return on investment (ROI) percentage? - Options: Users can specify their desired ROI",
            listOf(

                "5%",
                "10%",
                "15%"
            ),
        ),questions(
            " Are you interested in using leverage (borrowing money to invest) in your portfolio? -\n",
            listOf(

                "Yes, I'm open to using leverage.",
                        "No, I prefer not to use leverage."

            ),
        ),
    )

    var currentQuestionIndex by remember {
        mutableStateOf(0)
    }
    var score by remember { mutableStateOf(0) }
    val progress = currentQuestionIndex.toFloat() / questionsreal.size.toFloat()

    if (currentQuestionIndex < questionsreal.size) {
        val currentQuestion = questionsreal[currentQuestionIndex]
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(3.dp),
                color = Color(0xff50BEBF),
                progress = progress,
                trackColor = Color(0xffA0DCDC)
            )
            Spacer(Modifier.height(35.dp))
            Text(
                currentQuestion.text, Modifier.padding(horizontal = 12.dp), style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Tap to select",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
            currentQuestion.options.forEach { option ->
                OptionButton(
                    text = option,
                    onClick = {
                        currentQuestionIndex++
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    } else {
        afterFinish(score)
    }
}

@Composable
fun OptionButton(text: String, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
    {
        Button(
            onClick = { onClick() },
            Modifier
                .fillMaxWidth(0.9f)
                .height(55.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Color(0xff50BEBF))
        )
        {
            Text(text, color = Color.White, textAlign = TextAlign.Center)
        }
    }
}







