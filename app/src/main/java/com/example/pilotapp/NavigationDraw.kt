package com.example.pilotapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    val painterr = painterResource(id = R.drawable.backggnd_image)
    // TODO- to be replaced by the background image of our app header
    Card(
        modifier.fillMaxWidth().fillMaxHeight(0.232f),
        shape = RectangleShape,
        elevation = 5.dp
    )
    {
        Box(
            Modifier.fillMaxSize()

        ) {
            Image(
                painter = painterr, contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}



@Composable
fun DrawerBody(
    items: List<MenuItem>, modifier: Modifier = Modifier,
    itemtextStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        color = Color(0xFF023047),
        fontWeight = FontWeight.SemiBold
    ),
    onItemClick: (MenuItem) -> Unit
    // A lambda expression
) {

    LazyColumn(modifier)
    {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clickable { onItemClick(item) }
                    .padding(16.dp)
                    .padding(horizontal = 5.dp)
            )
            {
                Icon(imageVector = item.icon, contentDescription = item.description)

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = item.title, modifier = Modifier.weight(1f), style = itemtextStyle
                )
            }

        }
    }
}

