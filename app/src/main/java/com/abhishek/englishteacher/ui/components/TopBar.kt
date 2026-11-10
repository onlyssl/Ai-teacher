package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {}) {

            Icon(
                Icons.Default.Menu,
                contentDescription = null,
                tint = Color.White
            )

        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                "🔥 12",
                color = Color.White
            )

            Spacer(modifier = Modifier.width(20.dp))

            Surface(
                shape = CircleShape,
                color = Color(0xFF3D3D70)
            ) {

                IconButton(onClick = {}) {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White
                    )

                }

            }

        }

    }

}