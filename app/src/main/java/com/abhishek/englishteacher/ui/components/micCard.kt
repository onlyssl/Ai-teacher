package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MicCard(
    isListening: Boolean,
    isThinking: Boolean,
    onMicClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(220.dp)
            .clickable {
                onMicClick()
            },

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171B33)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(

            modifier = Modifier.fillMaxSize(),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            // =================================================
            // MICROPHONE
            // =================================================

            Box(

                modifier = Modifier
                    .size(100.dp)
                    .background(

                        brush = Brush.radialGradient(

                            colors = listOf(
                                Color(0xFF9A7BFF),
                                Color(0xFF6F4CFF)
                            )
                        ),

                        shape = CircleShape
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Mic,

                    contentDescription =
                        "Speak",

                    tint =
                        Color.White,

                    modifier =
                        Modifier.size(46.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.height(22.dp)
            )


            // =================================================
            // MAIN TEXT
            // =================================================

            Text(

                text = when {

                    isThinking ->
                        "Emma is thinking..."

                    isListening ->
                        "Listening..."

                    else ->
                        "Tap to Speak"
                },

                color =
                    Color.White,

                fontSize =
                    24.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            // =================================================
            // SUBTITLE
            // =================================================

            Text(

                text = when {

                    isThinking ->
                        "✨ Analyzing your English..."

                    isListening ->
                        "Speak now..."

                    else ->
                        "I'm ready to listen..."
                },

                color =
                    Color(0xFFB8B9C9),

                fontSize =
                    13.sp,

                fontWeight =
                    FontWeight.Normal
            )
        }
    }
}