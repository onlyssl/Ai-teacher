package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AITeacher() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "👩",
            fontSize = 90.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Emma - AI English Coach",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Hi! I'm Emma.\nLet's improve your English.",
            style = MaterialTheme.typography.bodyMedium
        )

    }

}