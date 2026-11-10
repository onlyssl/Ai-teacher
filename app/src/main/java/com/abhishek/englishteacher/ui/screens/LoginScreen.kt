package com.abhishek.englishteacher.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip

import com.abhishek.englishteacher.R
import com.abhishek.englishteacher.viewmodel.AuthViewModel


private val BackgroundDark = Color(0xFF080B20)
private val CardColor = Color(0xFF171A38)
private val Purple = Color(0xFF7C4DFF)
private val LightPurple = Color(0xFFA56BFF)
private val White = Color(0xFFF7F5FF)
private val Gray = Color(0xFFA9A7BA)
private val BorderGray = Color(0xFF62647A)


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var rememberMe by remember {
        mutableStateOf(true)
    }


    // =========================================================
    // FULL SCREEN
    // =========================================================

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070A1B),
                        Color(0xFF11133A),
                        Color(0xFF080B20)
                    )
                )
            )
    ) {


        // =====================================================
        // TOP HERO SECTION
        // =====================================================

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .weight(0.38f)
        ) {

            Image(

                painter = painterResource(
                    id = R.drawable.ai_teacher
                ),

                contentDescription =
                    "Emma AI English Coach",

                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.60f)
                    .align(Alignment.BottomEnd),

                contentScale =
                    ContentScale.Fit
            )


            Column(

                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .align(Alignment.CenterStart)
                    .padding(
                        start = 24.dp,
                        top = 8.dp
                    )
            ) {

                Text(
                    text = "Learn English",
                    color = White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    text = "with Emma",
                    color = LightPurple,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text = "Your AI English Coach",
                    color = Gray,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Box(

                    modifier = Modifier
                        .width(45.dp)
                        .height(3.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(Purple)
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Text(
                    text =
                        "“Every day is a\nstep toward fluency.”",

                    color = White,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }


        // =====================================================
        // LOGIN CARD
        // =====================================================

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .weight(0.62f)
                .padding(
                    horizontal = 14.dp
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp
                    )
                )
                .background(CardColor)
                .border(
                    width = 1.dp,
                    color = Purple.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp
                    )
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 14.dp
                )
        ) {


            // =================================================
            // TITLE
            // =================================================

            Text(
                text = "Welcome back! 👋",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text =
                    "Login to continue your learning journey.",

                color = Gray,
                fontSize = 13.sp
            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            // =================================================
            // EMAIL
            // =================================================

            OutlinedTextField(

                value = email,

                onValueChange = {
                    email = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                singleLine = true,

                placeholder = {

                    Text(
                        text = "Email or Phone",
                        color = Gray,
                        fontSize = 14.sp
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Email,

                        contentDescription =
                            "Email",

                        tint =
                            Gray
                    )
                },

                colors =
                    OutlinedTextFieldDefaults.colors(

                        // TYPED TEXT
                        focusedTextColor =
                            White,

                        unfocusedTextColor =
                            White,

                        // PLACEHOLDER
                        focusedPlaceholderColor =
                            Gray,

                        unfocusedPlaceholderColor =
                            Gray,

                        // ICON
                        focusedLeadingIconColor =
                            LightPurple,

                        unfocusedLeadingIconColor =
                            Gray,

                        // BORDER
                        focusedBorderColor =
                            Purple,

                        unfocusedBorderColor =
                            BorderGray,

                        // CURSOR
                        cursorColor =
                            LightPurple,

                        // BACKGROUND
                        focusedContainerColor =
                            Color.Transparent,

                        unfocusedContainerColor =
                            Color.Transparent
                    ),

                shape =
                    RoundedCornerShape(14.dp)
            )


            Spacer(
                modifier =
                    Modifier.height(9.dp)
            )


            // =================================================
            // PASSWORD
            // =================================================

            OutlinedTextField(

                value = password,

                onValueChange = {
                    password = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                singleLine = true,

                placeholder = {

                    Text(
                        text = "Password",
                        color = Gray,
                        fontSize = 14.sp
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Lock,

                        contentDescription =
                            "Password",

                        tint =
                            Gray
                    )
                },

                trailingIcon = {

                    IconButton(

                        onClick = {

                            passwordVisible =
                                !passwordVisible
                        }

                    ) {

                        Icon(

                            imageVector =
                                if (passwordVisible)
                                    Icons.Default.VisibilityOff
                                else
                                    Icons.Default.Visibility,

                            contentDescription =
                                if (passwordVisible)
                                    "Hide password"
                                else
                                    "Show password",

                            tint =
                                Gray
                        )
                    }
                },

                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                colors =
                    OutlinedTextFieldDefaults.colors(

                        // TYPED PASSWORD
                        focusedTextColor =
                            White,

                        unfocusedTextColor =
                            White,

                        // PLACEHOLDER
                        focusedPlaceholderColor =
                            Gray,

                        unfocusedPlaceholderColor =
                            Gray,

                        // ICONS
                        focusedLeadingIconColor =
                            LightPurple,

                        unfocusedLeadingIconColor =
                            Gray,

                        focusedTrailingIconColor =
                            LightPurple,

                        unfocusedTrailingIconColor =
                            Gray,

                        // BORDER
                        focusedBorderColor =
                            Purple,

                        unfocusedBorderColor =
                            BorderGray,

                        // CURSOR
                        cursorColor =
                            LightPurple,

                        // BACKGROUND
                        focusedContainerColor =
                            Color.Transparent,

                        unfocusedContainerColor =
                            Color.Transparent
                    ),

                shape =
                    RoundedCornerShape(14.dp)
            )


            // =================================================
            // REMEMBER / FORGOT
            // =================================================

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Checkbox(

                        checked =
                            rememberMe,

                        onCheckedChange = {
                            rememberMe = it
                        },

                        modifier =
                            Modifier.size(35.dp)
                    )

                    Text(
                        text = "Remember me",
                        color = Gray,
                        fontSize = 12.sp
                    )
                }


                TextButton(

                    onClick = {
                        // Password reset will be added next
                    }

                ) {

                    Text(
                        text = "Forgot Password?",
                        color = LightPurple,
                        fontSize = 12.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            // =================================================
            // ERROR
            // =================================================

            if (
                authViewModel
                    .errorMessage
                    .isNotEmpty()
            ) {

                Text(

                    text =
                        authViewModel.errorMessage,

                    color =
                        Color(0xFFFF6B6B),

                    fontSize =
                        12.sp,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 4.dp
                            )
                )
            }


            // =================================================
            // LOGIN BUTTON
            // =================================================

            Button(

                onClick = {

                    authViewModel.login(

                        email = email,

                        password = password

                    ) {

                        onLoginSuccess()
                    }
                },

                enabled =
                    !authViewModel.isLoading &&
                            email.isNotBlank() &&
                            password.isNotBlank(),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Purple,

                        disabledContainerColor =
                            Purple.copy(
                                alpha = 0.45f
                            )
                    )
            ) {

                if (
                    authViewModel.isLoading
                ) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(21.dp),

                        color =
                            Color.White,

                        strokeWidth =
                            2.dp
                    )

                } else {

                    Text(

                        text = "Log In",

                        color =
                            Color.White,

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(11.dp)
            )


            // =================================================
            // OR
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(

                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            BorderGray
                        )
                )

                Text(
                    text = "  or  ",
                    color = Gray,
                    fontSize = 12.sp
                )

                Box(

                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            BorderGray
                        )
                )
            }


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            // =================================================
            // GOOGLE / APPLE
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                SocialButton(

                    text = "G  Google",

                    modifier =
                        Modifier.weight(1f),

                    onClick = {

                        authViewModel.loginWithGoogle(
                            context = context
                        ) {

                            onLoginSuccess()
                        }
                    }
                )


                SocialButton(

                    text = "  Apple",

                    modifier =
                        Modifier.weight(1f)
                )
            }


            // =================================================
            // SIGN UP
            // =================================================

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(43.dp),

                horizontalArrangement =
                    Arrangement.Center,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text =
                        "Don't have an account?",

                    color =
                        Gray,

                    fontSize =
                        12.sp
                )


                TextButton(
                    onClick =
                        onRegisterClick
                ) {

                    Text(
                        text = "Sign Up",

                        color =
                            LightPurple,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            // =================================================
            // SECURITY
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.Center,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Security,

                    contentDescription =
                        "Security",

                    tint =
                        Gray,

                    modifier =
                        Modifier.size(16.dp)
                )


                Spacer(
                    modifier =
                        Modifier.width(5.dp)
                )


                Text(

                    text =
                        "Your data is safe and secure with us.",

                    color =
                        Gray,

                    fontSize =
                        11.sp
                )
            }
        }
    }
}


// =============================================================
// SOCIAL BUTTON
// =============================================================

@Composable
private fun SocialButton(

    text: String,

    modifier: Modifier = Modifier,

    onClick: () -> Unit = {}
) {

    TextButton(

        onClick = onClick,

        modifier = modifier
            .height(44.dp)
            .border(
                width = 1.dp,
                color = BorderGray,
                shape =
                    RoundedCornerShape(12.dp)
            ),

        shape =
            RoundedCornerShape(12.dp)
    ) {

        Text(

            text = text,

            color =
                White,

            fontSize =
                12.sp,

            fontWeight =
                FontWeight.Medium
        )
    }
}