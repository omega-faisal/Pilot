package com.example.pilotapp

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavitem(
    val name:String,
    val route:String,
    val icon:ImageVector,
    val BadgeCount:Int=0
)
