package com.abhishek.englishteacher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
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
fun PracticeScreen(
    onSpeakingClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF090B20),
                        Color(0xFF11152F),
                        Color(0xFF19133D)
                    )
                )
            )
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(
                horizontal = 16.dp,
                vertical = 18.dp
            )
    ) {

        // HEADER
        Text(
            text = "Practice with Emma",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Improve your English every day ✨",
            color = Color(0xFFB8B9C9),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // DAILY PRACTICE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF171A38)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFF8D5CFF)
                                    .copy(alpha = 0.18f),
                                RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "🔥",
                            fontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(13.dp))

                    Column {

                        Text(
                            text = "Today's Practice",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Complete one activity to maintain your streak.",
                            color = Color(0xFF9EA1B9),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    text = "Daily Goal",
                    color = Color(0xFFB8B9C9),
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(7.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            Color(0xFF303452),
                            RoundedCornerShape(50.dp)
                        )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.15f)
                            .height(8.dp)
                            .background(
                                Color(0xFF9C6CFF),
                                RoundedCornerShape(50.dp)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "0 of 1 completed",
                    color = Color(0xFF85889F),
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // SECTION TITLE
        Text(
            text = "Choose your practice",
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // SPEAKING + GRAMMAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            PracticeOptionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Mic,
                title = "Speaking",
                subtitle = "Talk with Emma",
                onClick = onSpeakingClick
            )

            PracticeOptionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Psychology,
                title = "Grammar",
                subtitle = "Fix your mistakes"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // VOCABULARY + LISTENING
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            PracticeOptionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.MenuBook,
                title = "Vocabulary",
                subtitle = "Learn new words"
            )

            PracticeOptionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Headphones,
                title = "Listening",
                subtitle = "Train your ears"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PROGRESS
        Text(
            text = "Your Progress",
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProgressCard(
            title = "Speaking",
            progress = 0.72f,
            percentage = "72%"
        )

        Spacer(modifier = Modifier.height(9.dp))

        ProgressCard(
            title = "Grammar",
            progress = 0.64f,
            percentage = "64%"
        )

        Spacer(modifier = Modifier.height(9.dp))

        ProgressCard(
            title = "Vocabulary",
            progress = 0.81f,
            percentage = "81%"
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun PracticeOptionCard(
    modifier: Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {

    Card(
        onClick = onClick,
        modifier = modifier.height(145.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171A38)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        Color(0xFF8D5CFF).copy(alpha = 0.17f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFFB58CFF),
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                color = Color(0xFF85889F),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun ProgressCard(
    title: String,
    progress: Float,
    percentage: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171A38)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 15.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.width(80.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(7.dp)
                    .background(
                        Color(0xFF303452),
                        RoundedCornerShape(50.dp)
                    )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(7.dp)
                        .background(
                            Color(0xFF9C6CFF),
                            RoundedCornerShape(50.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = percentage,
                color = Color(0xFFB58CFF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}