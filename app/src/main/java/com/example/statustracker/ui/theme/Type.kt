package com.example.statustracker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.statustracker.R

// Set of Material typography styles to start with

val QuickSand = FontFamily(
    Font(R.font.quicksand_regular),
    Font(R.font.quicksand_bold, weight = FontWeight.Bold),
    Font(R.font.quicksand_semibold, weight = FontWeight.SemiBold),
    Font(R.font.quicksand_medium, weight = FontWeight.Medium),
    Font(R.font.quicksand_light, weight = FontWeight.Light)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    caption = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
