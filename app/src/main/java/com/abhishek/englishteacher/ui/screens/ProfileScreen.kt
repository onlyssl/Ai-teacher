package com.abhishek.englishteacher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    userName: String,
    email: String,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1025))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color.White
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = userName.ifBlank { "Student" },
            color = Color.White,
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = email,
            color = Color.LightGray,
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "Logout"
            )

            Spacer(
                modifier = Modifier.height(0.dp)
            )

            Text(
                text = "  Logout"
            )
        }
    }
}