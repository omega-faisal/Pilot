package com.example.pilotapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    //onActionIconClick:()-> Unit
) {
    val pilotlogo_removed = painterResource(id = R.drawable.pilothead_removed)
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
            Text(text = "Pilot", fontSize = 21.sp)

        },
        backgroundColor = Color(0xff01ACAD),
        contentColor = MaterialTheme.colors.onPrimary,

        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
        }


//        ,
//        actions = {
//            IconButton(onClick = onActionIconClick
//            ) {
//                Icon(
//                    imageVector= filter_icon,
//                    contentDescription = null
//                )
//            }
//        }
    )
}