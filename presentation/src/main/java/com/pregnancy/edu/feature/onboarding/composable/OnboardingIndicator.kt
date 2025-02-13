package com.pregnancy.edu.feature.onboarding.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    currentIdx: Int
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageSize) { idx ->
            val selected = currentIdx == idx
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (selected) 10.dp else 8.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(if (selected) 10.dp else 8.dp),
                    shape = MaterialTheme.shapes.small,
                    color = if (selected) {
                        Color(0xFFFAACAA)
                    } else {
                        Color.LightGray
                    }
                ) { }
            }
        }
    }
}