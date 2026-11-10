package com.abhishek.englishteacher.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abhishek.englishteacher.R
import com.abhishek.englishteacher.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    activity: android.app.Activity,
    onRegistrationSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    onOtpSent: (String, String) -> Unit,
    onPhoneVerificationStart: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var validationMessage by remember { mutableStateOf("") }

    // Email validation
    fun isValidEmail(value: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(value.trim())
            .matches()
    }

    // Indian mobile number validation
    fun isValidIndianMobile(value: String): Boolean {
        return value.matches(Regex("^[6-9][0-9]{9}$"))
    }

    // Password validation
    fun isValidPassword(value: String): Boolean {
        return value.length >= 6
    }

    val isFormValid =
        name.trim().isNotEmpty() &&
                isValidEmail(email) &&
                isValidIndianMobile(mobileNumber) &&
                isValidPassword(password) &&
                password == confirmPassword

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
                    horizontal = 18.dp,
                    vertical = 6.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.ai_teacher
                ),
                contentDescription = "Emma AI English Teacher",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(78.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Your Account",
                    color = Color(0xFFB58CFF),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Start your English journey with Emma",
                color = Color(0xFFB8B9C9),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9C6CFF))
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "\"Every conversation brings you closer to fluency.\"",
                color = Color(0xFFAAAAC0),
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(23.dp))
                    .background(
                        Color(0xFF171A38).copy(alpha = 0.97f)
                    )
                    .padding(
                        horizontal = 13.dp,
                        vertical = 9.dp
                    )
            ) {

                Text(
                    text = "Create your account ✨",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Join Emma and start speaking English confidently.",
                    color = Color(0xFF9EA1B9),
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(7.dp))

                // FULL NAME
                RegisterTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        validationMessage = ""
                    },
                    label = "Full Name",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }
                )

                if (name.isNotEmpty() && name.trim().isEmpty()) {
                    ValidationText("Please enter your full name.")
                }

                Spacer(modifier = Modifier.height(4.dp))

                // EMAIL
                RegisterTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        validationMessage = ""
                    },
                    label = "Email Address",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    }
                )

                if (
                    email.isNotEmpty() &&
                    !isValidEmail(email)
                ) {
                    ValidationText("Please enter a valid email address.")
                }

                Spacer(modifier = Modifier.height(4.dp))

                // MOBILE NUMBER
                RegisterTextField(
                    value = mobileNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            mobileNumber = it
                            validationMessage = ""
                        }
                    },
                    label = "Mobile Number",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    }
                )

                if (
                    mobileNumber.isNotEmpty() &&
                    !isValidIndianMobile(mobileNumber)
                ) {
                    ValidationText(
                        "Enter a valid 10-digit Indian mobile number."
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // PASSWORD
                RegisterTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        validationMessage = ""
                    },
                    label = "Password",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,
                                contentDescription =
                                    "Toggle password visibility"
                            )
                        }
                    }
                )

                if (
                    password.isNotEmpty() &&
                    !isValidPassword(password)
                ) {
                    ValidationText(
                        "Password must contain at least 6 characters."
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // CONFIRM PASSWORD
                RegisterTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        validationMessage = ""
                    },
                    label = "Confirm Password",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    visualTransformation =
                        if (confirmPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisible =
                                    !confirmPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (confirmPasswordVisible)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,
                                contentDescription =
                                    "Toggle password visibility"
                            )
                        }
                    }
                )

                if (
                    confirmPassword.isNotEmpty() &&
                    password != confirmPassword
                ) {
                    ValidationText(
                        "Passwords do not match."
                    )
                }

                // AUTH ERROR
                if (authViewModel.errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = authViewModel.errorMessage,
                        color = Color(0xFFFF8A9A),
                        fontSize = 10.sp
                    )
                }

                // VALIDATION MESSAGE
                if (validationMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = validationMessage,
                        color = Color(0xFFFF8A9A),
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                // CREATE ACCOUNT BUTTON
                Button(
                    onClick = {

                        when {
                            name.trim().isEmpty() -> {
                                validationMessage =
                                    "Please enter your full name."
                            }

                            !isValidEmail(email) -> {
                                validationMessage =
                                    "Please enter a valid email address."
                            }

                            !isValidIndianMobile(mobileNumber) -> {
                                validationMessage =
                                    "Enter a valid 10-digit Indian mobile number."
                            }

                            !isValidPassword(password) -> {
                                validationMessage =
                                    "Password must contain at least 6 characters."
                            }

                            password != confirmPassword -> {
                                validationMessage =
                                    "Passwords do not match."
                            }

                            else -> {

                                validationMessage = ""

                                authViewModel.register(
                                    name = name.trim(),
                                    email = email.trim(),
                                    password = password
                                ) {

                                    val formattedPhone = "+91${mobileNumber}"

                                    // Show our beautiful verification screen first

                                    authViewModel.sendPhoneOtp(
                                        activity = activity,
                                        phoneNumber = formattedPhone
                                    ) { verificationId ->

                                        onOtpSent(
                                            verificationId,
                                            formattedPhone
                                        )
                                    }
                                }
                            }
                        }
                    },
                    enabled =
                        !authViewModel.isLoading &&
                                isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8D5CFF),
                        disabledContainerColor = Color(0xFF4C416A)
                    )
                ) {

                    if (authViewModel.isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(19.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "Create Account",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(7.dp))

                // OR CONTINUE WITH
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFF383B5B)
                    )

                    Text(
                        text = "  or continue with  ",
                        color = Color(0xFF85889F),
                        fontSize = 9.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFF383B5B)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    SocialButton(
                        text = "G  Google",
                        modifier = Modifier.weight(1f)
                    )

                    SocialButton(
                        text = "  Apple",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Already have an account?",
                    color = Color(0xFF9EA1B9),
                    fontSize = 11.sp
                )

                TextButton(
                    onClick = onLoginClick
                ) {

                    Text(
                        text = "Log In",
                        color = Color(0xFFB58CFF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "🔒 Your information is securely protected",
                color = Color(0xFF777A91),
                fontSize = 8.sp
            )
        }
    }
}

@Composable
private fun ValidationText(
    message: String
) {
    Spacer(modifier = Modifier.height(2.dp))

    Text(
        text = message,
        color = Color(0xFFFF8A9A),
        fontSize = 9.sp
    )
}

@Composable
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation =
        VisualTransformation.None
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = Color(0xFF9295AC),
                fontSize = 12.sp
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF9C6CFF),
            unfocusedBorderColor = Color(0xFF393C5B),
            focusedLabelColor = Color(0xFFB58CFF),
            unfocusedLabelColor = Color(0xFF9295AC),
            focusedLeadingIconColor = Color(0xFFB58CFF),
            unfocusedLeadingIconColor = Color(0xFF777A91),
            focusedTrailingIconColor = Color(0xFFB58CFF),
            unfocusedTrailingIconColor = Color(0xFF777A91),
            cursorColor = Color(0xFFB58CFF),
            focusedContainerColor = Color(0xFF10132D),
            unfocusedContainerColor = Color(0xFF10132D)
        )
    )
}

@Composable
private fun SocialButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(37.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF242744))
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}