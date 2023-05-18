package com.aeml.lolatools.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val replyTypography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.ExtraLight,
        fontSize = 60. sp,
        lineHeight = 50. sp,
        letterSpacing = 12. sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.ExtraLight,
        fontSize = 40. sp,
        lineHeight = 24. sp,
        letterSpacing = 0.20.sp

    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.ExtraLight,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 5.sp,
        letterSpacing = 0.50.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 22.sp
    )

)

