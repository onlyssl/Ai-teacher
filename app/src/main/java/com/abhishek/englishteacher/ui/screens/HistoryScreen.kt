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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen() {

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
            text = "Your History",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Track your English learning journey with Emma",
            color = Color(0xFFB8B9C9),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        // SUMMARY CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF171A38)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 20.dp
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                HistorySummaryItem(
                    value = "0",
                    title = "Sessions"
                )

                SummaryDivider()

                HistorySummaryItem(
                    value = "0",
                    title = "Minutes"
                )

                SummaryDivider()

                HistorySummaryItem(
                    value = "0",
                    title = "XP Earned"
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // SECTION TITLE
        Text(
            text = "Recent Practice",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // EMPTY STATE
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF171A38)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 32.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            Color(0xFF8D5CFF).copy(alpha = 0.15f),
                            RoundedCornerShape(22.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "📚",
                        fontSize = 34.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No practice yet",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Complete your first practice session\nwith Emma and it will appear here.",
                    color = Color(0xFF9EA1B9),
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // PRACTICE TYPES
        Text(
            text = "Practice Types",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        HistoryTypeCard(
            icon = Icons.Default.Mic,
            title = "Speaking",
            subtitle = "Practice conversations with Emma"
        )

        Spacer(modifier = Modifier.height(10.dp))

        HistoryTypeCard(
            icon = Icons.Default.Psychology,
            title = "Grammar",
            subtitle = "Improve your grammar skills"
        )

        Spacer(modifier = Modifier.height(10.dp))

        HistoryTypeCard(
            icon = Icons.Default.MenuBook,
            title = "Vocabulary",
            subtitle = "Learn and review new words"
        )

        Spacer(modifier = Modifier.height(10.dp))

        HistoryTypeCard(
            icon = Icons.Default.Headphones,
            title = "Listening",
            subtitle = "Improve your listening skills"
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}


// =========================================================
// HISTORY SUMMARY ITEM
// =========================================================

@Composable
private fun HistorySummaryItem(
    value: String,
    title: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = title,
            color = Color(0xFF9EA1B9),
            fontSize = 11.sp
        )
    }
}


// =========================================================
// SUMMARY DIVIDER
// =========================================================

@Composable
private fun SummaryDivider() {

    Box(
        modifier = Modifier
            .width(1.dp)
            .height(38.dp)
            .background(Color(0xFF30365A))
    )
}


// =========================================================
// PRACTICE TYPE CARD
// =========================================================

@Composable
private fun HistoryTypeCard(
    icon: ImageVector,
    title: String,
    subtitle: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171A38)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF8D5CFF).copy(alpha = 0.17f),
                        RoundedCornerShape(15.dp)
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

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = subtitle,
                    color = Color(0xFF85889F),
                    fontSize = 10.sp
                )
            }

            Text(
                text = "0",
                color = Color(0xFFB58CFF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}