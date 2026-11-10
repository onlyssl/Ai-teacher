package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MicButton(
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(90.dp)
            .shadow(10.dp, CircleShape)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "🎤",
            fontSize = 42.sp
        )

    }

}