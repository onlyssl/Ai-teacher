package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.abhishek.englishteacher.R


@Composable
fun HeroSection(
    userName: String,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .background(
                Color(0xFF090A1A)
            )
    ) {

        // =====================================================
        // TOP BAR
        // =====================================================

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            // MENU BUTTON

            IconButton(
                onClick = onMenuClick
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Menu,

                    contentDescription =
                        "Open menu",

                    tint =
                        Color.White,

                    modifier =
                        Modifier.size(32.dp)
                )
            }


            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "🔥 12",

                    color =
                        Color.White,

                    fontSize =
                        24.sp
                )


                Spacer(
                    modifier =
                        Modifier.width(20.dp)
                )


                Box(

                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            Color(0xFF5951B7),
                            CircleShape
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    IconButton(
                        onClick = onProfileClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }


        // =====================================================
        // PURPLE GLOW
        // =====================================================

        Box(

            modifier = Modifier
                .size(320.dp)
                .offset(
                    x = (-80).dp,
                    y = 140.dp
                )
                .background(

                    brush =
                        Brush.radialGradient(

                            colors = listOf(
                                Color(0xFF6F4CFF),
                                Color.Transparent
                            )
                        ),

                    shape =
                        CircleShape
                )
        )


        // =====================================================
        // AI TEACHER
        // =====================================================

        Image(

            painter =
                painterResource(
                    R.drawable.ai_teacher
                ),

            contentDescription =
                null,

            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .height(260.dp)
                .offset(
                    x = 10.dp,
                    y = 25.dp
                ),

            contentScale =
                ContentScale.Fit
        )


        // =====================================================
        // EMMA TEXT
        // =====================================================

        Column(

            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(
                    top = 70.dp,
                    end = 20.dp
                )
                .width(190.dp)
        ) {

            Text(
                text = "Hello,",

                color =
                    Color.White,

                fontSize =
                    34.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Text(
                text = "$userName! 👋",

                color =
                    Color(0xFF8A6BFF),

                fontSize =
                    34.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            Text(
                text = "I'm Emma",

                color =
                    Color.White,

                fontSize =
                    24.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            Text(
                text = "Your AI English Coach",

                color =
                    Color.LightGray,

                fontSize =
                    16.sp
            )


            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )
        }
    }
}