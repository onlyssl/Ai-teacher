package com.abhishek.englishteacher.viewmodel

import android.app.Activity
import android.content.Context

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.abhishek.englishteacher.repository.AuthRepository

import kotlinx.coroutines.launch


class AuthViewModel : ViewModel() {

    private val repository =
        AuthRepository()


    // =========================================================
    // STATE
    // =========================================================

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(
        repository.isUserLoggedIn()
    )
        private set

    var userName by mutableStateOf("")
        private set
    var streak by mutableStateOf(0)
        private set
    fun loadStreak() {

        viewModelScope.launch {

            val result = repository.getStreak()

            result
                .onSuccess { value ->
                    streak = value
                }
                .onFailure {
                    streak = 0
                }
        }
    }
    // =========================================================
    // GOOGLE LOGIN
    // =========================================================

    fun loginWithGoogle(
        context: Context,
        onSuccess: () -> Unit
    ) {

        errorMessage = ""
        isLoading = true

        viewModelScope.launch {

            val result =
                repository.loginWithGoogle(
                    context
                )

            isLoading = false

            result
                .onSuccess {

                    isLoggedIn = true

                    loadUserName()

                    onSuccess()
                }
                .onFailure { exception ->

                    errorMessage =
                        exception.message
                            ?: "Google sign-in failed."
                }
        }
    }


    // =========================================================
    // REGISTER USER
    // =========================================================

    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (name.isBlank()) {

            errorMessage =
                "Please enter your name"

            return
        }

        if (email.isBlank()) {

            errorMessage =
                "Please enter your email"

            return
        }

        if (password.length < 6) {

            errorMessage =
                "Password must be at least 6 characters"

            return
        }

        errorMessage = ""
        isLoading = true

        viewModelScope.launch {

            val result =
                repository.registerUser(
                    name = name.trim(),
                    email = email.trim(),
                    password = password
                )

            isLoading = false

            result
                .onSuccess {

                    /*
                     * IMPORTANT:
                     *
                     * We DO NOT set isLoggedIn = true here.
                     *
                     * The user must first verify their
                     * mobile number using OTP.
                     */

                    userName =
                        name.trim()

                    onSuccess()
                }
                .onFailure { exception ->

                    errorMessage =
                        getFriendlyErrorMessage(
                            exception
                        )
                }
        }
    }


    // =========================================================
    // SEND PHONE OTP
    // =========================================================

    fun sendPhoneOtp(
        activity: Activity,
        phoneNumber: String,
        onCodeSent: (String) -> Unit
    ) {

        errorMessage = ""
        isLoading = true

        repository.sendPhoneOtp(
            activity = activity,
            phoneNumber = phoneNumber,

            onCodeSent = { verificationId ->

                isLoading = false

                onCodeSent(
                    verificationId
                )
            },

            onVerificationFailed = { message ->

                isLoading = false

                errorMessage =
                    message
            }
        )
    }


    // =========================================================
    // VERIFY PHONE OTP
    // =========================================================

    fun verifyPhoneOtp(
        verificationId: String,
        otp: String,
        onSuccess: () -> Unit
    ) {

        if (otp.length != 6) {

            errorMessage =
                "Please enter the 6-digit OTP."

            return
        }

        errorMessage = ""
        isLoading = true

        viewModelScope.launch {

            val result =
                repository.verifyPhoneOtp(
                    verificationId = verificationId,
                    otp = otp
                )

            isLoading = false

            result
                .onSuccess {

                    onSuccess()
                }
                .onFailure { exception ->

                    errorMessage =
                        exception.message
                            ?: "Invalid OTP. Please try again."
                }
        }
    }


    // =========================================================
    // LOGIN
    // =========================================================

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank()) {

            errorMessage =
                "Please enter your email"

            return
        }

        if (password.isBlank()) {

            errorMessage =
                "Please enter your password"

            return
        }

        errorMessage = ""
        isLoading = true

        viewModelScope.launch {

            val result =
                repository.loginUser(
                    email = email.trim(),
                    password = password
                )

            isLoading = false

            result
                .onSuccess {

                    isLoggedIn = true

                    loadUserName()

                    onSuccess()
                }
                .onFailure { exception ->

                    errorMessage =
                        getFriendlyErrorMessage(
                            exception
                        )
                }
        }
    }


    // =========================================================
    // LOAD USER NAME
    // =========================================================

    fun loadUserName() {

        viewModelScope.launch {

            userName =
                repository.getUserName()
                    ?: ""
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    fun logout() {

        repository.logout()

        userName = ""

        isLoggedIn = false

        errorMessage = ""
    }


    // =========================================================
    // FRIENDLY FIREBASE ERRORS
    // =========================================================

    private fun getFriendlyErrorMessage(
        exception: Throwable
    ): String {

        return when {

            exception.message
                ?.contains(
                    "email-already-in-use",
                    true
                ) == true ->
                "This email is already registered."

            exception.message
                ?.contains(
                    "invalid-email",
                    true
                ) == true ->
                "Please enter a valid email address."

            exception.message
                ?.contains(
                    "weak-password",
                    true
                ) == true ->
                "Password is too weak. Use at least 6 characters."

            exception.message
                ?.contains(
                    "invalid-credential",
                    true
                ) == true ->
                "Email or password is incorrect."

            exception.message
                ?.contains(
                    "network",
                    true
                ) == true ->
                "Please check your internet connection."

            else ->
                "Something went wrong. Please try again."
        }
    }
}