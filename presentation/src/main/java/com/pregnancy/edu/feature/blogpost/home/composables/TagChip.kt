package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TagChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        modifier = modifier,
        color = Color(0x33AAAAAA), // Semi-transparent gray
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}