package com.example.pilotapp

import androidx.compose.ui.graphics.painter.Painter

data class CourseCard(
    val name:String,
    val price:Int,
    val courseImage:Painter,
    val description:String,
    val duration:String
)
