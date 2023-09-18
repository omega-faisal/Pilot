package com.example.pilotapp.ui.theme

import androidx.compose.ui.graphics.painter.Painter

data class ChartTypes(
    val picture:Painter,
    val name:String,
    val detail:String,
    val islast:Boolean=false
    )
