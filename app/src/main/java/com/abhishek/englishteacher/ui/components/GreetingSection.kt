package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreetingSection() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Hello, Abhishek 👋",
            color = Color.White,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "I'm Emma, your AI English Coach.",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Let's improve your English together!",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )

    }

}