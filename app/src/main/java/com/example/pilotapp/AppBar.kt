package com.example.pilotapp

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

@Composable
fun AppBar(
    onNavigationIconClick: () ->Unit,
    //onActionIconClick:()-> Unit
)
{
    //val filter_icon=ImageVector.vectorResource(id = R.drawable.filter_icon)
    TopAppBar(
        title = {
            Text(text = "PILOT")
        },
        backgroundColor = Color(0xff01ACAD),
        contentColor = MaterialTheme.colors.onPrimary,

        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector= Icons.Default.Menu,
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