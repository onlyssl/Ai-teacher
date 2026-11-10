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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abhishek.englishteacher.viewmodel.AuthViewModel


@Composable
fun OtpVerificationScreen(
    phoneNumber: String,
    verificationId: String,
    onVerificationSuccess: () -> Unit,
    onBackClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    var otp by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Box(
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // BACK BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // PHONE ICON
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(
                        RoundedCornerShape(45.dp)
                    )
                    .background(
                        Color(0xFF8D5CFF)
                            .copy(alpha = 0.18f)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = Color(0xFFB58CFF),
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // TITLE
            Text(
                text = "Verify Your Mobile",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "We've sent a 6-digit OTP to",
                color = Color(0xFF9EA1B9),
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = maskPhoneNumber(phoneNumber),
                color = Color(0xFFB58CFF),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // OTP CARD
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(23.dp)
                    )
                    .background(
                        Color(0xFF171A38)
                    )
                    .padding(
                        horizontal = 18.dp,
                        vertical = 22.dp
                    ),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Enter Verification Code",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Enter the OTP received on your mobile.",
                    color = Color(0xFF85889F),
                    fontSize = 11.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // OTP FIELD
                OutlinedTextField(
                    value = otp,
                    onValueChange = {

                        if (
                            it.length <= 6 &&
                            it.all { char ->
                                char.isDigit()
                            }
                        ) {

                            otp = it
                            errorMessage = ""
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    singleLine = true,

                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),

                    label = {
                        Text(
                            text = "6-Digit OTP",
                            color = Color(0xFF9295AC)
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number,
                            imeAction =
                                ImeAction.Done
                        ),

                    leadingIcon = {
                        Icon(
                            imageVector =
                                Icons.Default.PhoneAndroid,
                            contentDescription = null
                        )
                    },

                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                Color(0xFF9C6CFF),

                            unfocusedBorderColor =
                                Color(0xFF393C5B),

                            focusedLabelColor =
                                Color(0xFFB58CFF),

                            unfocusedLabelColor =
                                Color(0xFF9295AC),

                            focusedLeadingIconColor =
                                Color(0xFFB58CFF),

                            unfocusedLeadingIconColor =
                                Color(0xFF777A91),

                            cursorColor =
                                Color(0xFFB58CFF),

                            focusedContainerColor =
                                Color(0xFF10132D),

                            unfocusedContainerColor =
                                Color(0xFF10132D)
                        ),

                    shape =
                        RoundedCornerShape(13.dp)
                )

                // LOCAL ERROR
                if (errorMessage.isNotEmpty()) {

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text = errorMessage,
                        color = Color(0xFFFF8A9A),
                        fontSize = 10.sp
                    )
                }

                // VIEWMODEL ERROR
                if (
                    authViewModel.errorMessage.isNotEmpty()
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            authViewModel.errorMessage,
                        color = Color(0xFFFF8A9A),
                        fontSize = 10.sp
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )

                // VERIFY BUTTON
                Button(
                    onClick = {

                        if (otp.length != 6) {

                            errorMessage =
                                "Please enter the 6-digit OTP."

                        } else {

                            errorMessage = ""

                            authViewModel.verifyPhoneOtp(
                                verificationId =
                                    verificationId,

                                otp = otp
                            ) {

                                onVerificationSuccess()
                            }
                        }
                    },

                    enabled =
                        !authViewModel.isLoading &&
                                otp.length == 6,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),

                    shape =
                        RoundedCornerShape(13.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF8D5CFF),

                            disabledContainerColor =
                                Color(0xFF4C416A)
                        )
                ) {

                    if (authViewModel.isLoading) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(20.dp),

                            color = Color.White,

                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "Verify Number",
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            "Didn't receive the code?",
                        color =
                            Color(0xFF85889F),
                        fontSize = 11.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )

                    Text(
                        text = "Resend OTP",
                        color =
                            Color(0xFFB58CFF),
                        fontSize = 11.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            Text(
                text =
                    "🔒 Your verification is secure",
                color =
                    Color(0xFF777A91),
                fontSize = 9.sp
            )
        }
    }
}
private fun maskPhoneNumber(phoneNumber: String): String {

    return if (phoneNumber.length >= 10) {
        phoneNumber.take(3) +
                " •••• ••• " +
                phoneNumber.takeLast(3)
    } else {
        phoneNumber
    }
}