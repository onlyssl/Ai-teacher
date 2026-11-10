package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsCard(
    streak: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171B33)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier.weight(1f)) {
                StatItem("🔥", streak.toString(), "Day")
            }

            VerticalDivider()

            Box(modifier = Modifier.weight(1f)) {
                StatItem("⭐", "850", "XP")
            }

            VerticalDivider()

            Box(modifier = Modifier.weight(1f)) {
                StatItem("🏆", "4", "Level")
            }

        }

    }

}

@Composable
fun VerticalDivider() {

    Box(
        modifier = Modifier
            .width(1.dp)
            .height(55.dp)
            .background(Color(0xFF30365A))
    )

}

@Composable
fun StatItem(
    emoji: String,
    value: String,
    title: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = emoji,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            color = Color(0xFFB7BBD8),
            fontSize = 13.sp
        )

    }

}