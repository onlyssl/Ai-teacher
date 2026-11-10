package com.abhishek.englishteacher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.abhishek.englishteacher.ui.screens.HomeScreen
import com.abhishek.englishteacher.ui.screens.LoginScreen
import com.abhishek.englishteacher.ui.screens.OtpVerificationScreen
import com.abhishek.englishteacher.ui.screens.PhoneVerificationLoadingScreen
import com.abhishek.englishteacher.ui.screens.RegisterScreen

import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // =====================================================
            // SCREEN STATE
            // =====================================================

            var showRegister by remember {
                mutableStateOf(false)
            }

            var showOtp by remember {
                mutableStateOf(false)
            }

            var showPhoneVerificationLoading by remember {
                mutableStateOf(false)
            }

            // Firebase verification ID
            var verificationId by remember {
                mutableStateOf("")
            }

            // Phone number being verified
            var verifiedPhoneNumber by remember {
                mutableStateOf("")
            }

            var isLoggedIn by remember {
                mutableStateOf(
                    FirebaseAuth
                        .getInstance()
                        .currentUser != null
                )
            }


            // =====================================================
            // SCREEN NAVIGATION
            // =====================================================

            when {

                // -------------------------------------------------
                // HOME
                // -------------------------------------------------

                isLoggedIn -> {

                    HomeScreen(
                        onLogout = {

                            FirebaseAuth
                                .getInstance()
                                .signOut()

                            isLoggedIn = false
                            showRegister = false
                            showOtp = false
                            showPhoneVerificationLoading = false

                            verificationId = ""
                            verifiedPhoneNumber = ""
                        }
                    )
                }


                // -------------------------------------------------
                // PHONE VERIFICATION LOADING
                // -------------------------------------------------

                showPhoneVerificationLoading -> {

                    PhoneVerificationLoadingScreen()
                }


                // -------------------------------------------------
                // OTP VERIFICATION
                // -------------------------------------------------

                showOtp -> {

                    OtpVerificationScreen(

                        phoneNumber =
                            verifiedPhoneNumber,

                        verificationId =
                            verificationId,

                        onVerificationSuccess = {

                            // OTP successfully verified
                            isLoggedIn = true

                            showOtp = false
                            showPhoneVerificationLoading = false

                            verificationId = ""
                            verifiedPhoneNumber = ""
                        },

                        onBackClick = {

                            showOtp = false
                            showPhoneVerificationLoading = false

                            showRegister = true
                        }
                    )
                }


                // -------------------------------------------------
                // REGISTER
                // -------------------------------------------------

                showRegister -> {

                    RegisterScreen(

                        activity = this@MainActivity,

                        onRegistrationSuccess = {

                            // Registration completed
                            // Phone verification is handled separately
                            showRegister = false
                        },

                        onLoginClick = {

                            showRegister = false
                            showPhoneVerificationLoading = false
                        },

                        // -------------------------------------------------
                        // START PHONE VERIFICATION
                        // -------------------------------------------------

                        onPhoneVerificationStart = {

                            showRegister = false
                            showPhoneVerificationLoading = true
                        },

                        // -------------------------------------------------
                        // OTP SENT
                        // -------------------------------------------------

                        onOtpSent = { id, phone ->

                            // Save Firebase verification ID
                            verificationId = id

                            // Save phone number
                            verifiedPhoneNumber = phone

                            // Hide our loading screen
                            showPhoneVerificationLoading = false

                            // Open OTP screen
                            showRegister = false
                            showOtp = true
                        }
                    )
                }


                // -------------------------------------------------
                // LOGIN
                // -------------------------------------------------

                else -> {

                    LoginScreen(

                        onLoginSuccess = {

                            isLoggedIn = true
                        },

                        onRegisterClick = {

                            showRegister = true
                        }
                    )
                }
            }
        }
    }
}