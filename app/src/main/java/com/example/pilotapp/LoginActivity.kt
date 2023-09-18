package com.example.pilotapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilotapp.ui.theme.ChartsAndCandles
import com.example.pilotapp.ui.theme.PilotAppTheme
import com.example.pilotapp.ui.theme.QuestionsActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                val loginimage = painterResource(id = R.drawable.androidsmall)
                Box(Modifier.fillMaxSize())
                {
                    Image(
                        painter = loginimage,
                        contentDescription = null,
                        Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    Column() {
                        Spacer(Modifier.height(200.dp))
                        logincard(onbuttonclick = { name ->
                            val intent = Intent(this@LoginActivity, QuestionsActivity::class.java)
                            intent.putExtra("USERNAME", name)
                            startActivity(intent)
                        })

                    }

                }

            }
        }
    }
}

@Composable
fun logincard(
    onbuttonclick: (String) -> Unit
) {
    val context = LocalContext.current
    Row()
    {
        var Cur_context = LocalContext.current
        var phonenumber by remember {
            mutableStateOf("")
        }
        var otp by remember {
            mutableStateOf("")
        }
        Spacer(Modifier.width(20.dp))
        Card(
            Modifier
                .height(350.dp)
                .width(350.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 5.dp
        )
        {
            Column(Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Login",
                    Modifier.padding(horizontal = 20.dp),
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Phone Number",
                    Modifier.padding(horizontal = 30.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(value = phonenumber, onValueChange = { phonenumber = it },
                    Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(0.9f),
                    placeholder = { Text(text = "Enter your phone number", color = Color.Gray) })

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "One Time Password",
                    Modifier.padding(horizontal = 30.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(value = otp, onValueChange = { otp = it },
                    Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(0.9f),
                    placeholder = { Text(text = "OTP", color = Color.Gray) })

                Spacer(modifier = Modifier.height(13.dp))
                Row()
                {
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = {
                            if (phonenumber.isEmpty() || otp.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please enter number and otp first",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                onbuttonclick(phonenumber)
                            }
                        },
                        Modifier.fillMaxWidth(0.9f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff50BEBF)),
                        shape = RoundedCornerShape(20.dp)
                    )
                    {
                        Text("Login", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }


    }
}