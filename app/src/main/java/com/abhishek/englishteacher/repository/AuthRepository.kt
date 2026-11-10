package com.abhishek.englishteacher.repository

import android.app.Activity
import android.content.Context

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest

import com.abhishek.englishteacher.R
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

import kotlinx.coroutines.tasks.await

import java.util.concurrent.TimeUnit


class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    private val firestore =
        FirebaseFirestore.getInstance()


    // =========================================================
    // REGISTER USER
    // =========================================================

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Result<String> {

        return try {

            val result = auth
                .createUserWithEmailAndPassword(
                    email,
                    password
                )
                .await()

            val userId =
                result.user?.uid
                    ?: throw Exception(
                        "User ID not found"
                    )

            val userData =
                hashMapOf(
                    "name" to name,
                    "email" to email,
                    "createdAt" to
                            System.currentTimeMillis()
                )

            firestore
                .collection("users")
                .document(userId)
                .set(userData)
                .await()

            Result.success(userId)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // GOOGLE LOGIN
    // =========================================================

    suspend fun loginWithGoogle(
        context: Context
    ): Result<String> {

        return try {

            val credentialManager =
                CredentialManager.create(context)

            val signInWithGoogleOption =
                GetSignInWithGoogleOption.Builder(
                    serverClientId =
                        context.getString(
                            R.string.default_web_client_id
                        )
                )
                    .build()

            val request =
                GetCredentialRequest.Builder()
                    .addCredentialOption(
                        signInWithGoogleOption
                    )
                    .build()

            val result =
                credentialManager.getCredential(
                    context,
                    request
                )

            val credential =
                result.credential

            if (
                credential is CustomCredential &&
                credential.type ==
                GoogleIdTokenCredential
                    .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                val googleCredential =
                    GoogleIdTokenCredential
                        .createFrom(
                            credential.data
                        )

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        googleCredential.idToken,
                        null
                    )

                val authResult =
                    auth.signInWithCredential(
                        firebaseCredential
                    ).await()

                val firebaseUser =
                    authResult.user
                        ?: throw Exception(
                            "Google user not found"
                        )

                val userId =
                    firebaseUser.uid

                val userData =
                    hashMapOf(
                        "name" to (
                                firebaseUser.displayName
                                    ?: ""
                                ),

                        "email" to (
                                firebaseUser.email
                                    ?: ""
                                ),

                        "createdAt" to
                                System.currentTimeMillis()
                    )

                firestore
                    .collection("users")
                    .document(userId)
                    .set(
                        userData,
                        SetOptions.merge()
                    )
                    .await()

                Result.success(userId)

            } else {

                Result.failure(
                    Exception(
                        "Google account credential not found"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // LOGIN USER
    // =========================================================

    suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {

        return try {

            val result =
                auth.signInWithEmailAndPassword(
                    email,
                    password
                ).await()

            val userId =
                result.user?.uid
                    ?: throw Exception(
                        "User ID not found"
                    )

            Result.success(userId)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    // =========================================================
    // GET USER NAME
    // =========================================================

    suspend fun getUserName(): String? {

        val userId =
            auth.currentUser?.uid
                ?: return null

        return try {

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .get()
                    .await()

            document.getString("name")

        } catch (e: Exception) {

            null
        }
    }


    // =========================================================
    // SEND PHONE OTP
    // =========================================================

    fun sendPhoneOtp(
        activity: Activity,
        phoneNumber: String,
        onCodeSent: (String) -> Unit,
        onVerificationFailed: (String) -> Unit
    ) {

        val options =
            PhoneAuthOptions
                .newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(
                    60L,
                    TimeUnit.SECONDS
                )
                .setActivity(activity)
                .setCallbacks(
                    object :
                        PhoneAuthProvider
                        .OnVerificationStateChangedCallbacks() {

                        override fun onVerificationCompleted(
                            credential: PhoneAuthCredential
                        ) {

                            // Automatic verification.
                            // We will handle this later.
                        }

                        override fun onVerificationFailed(
                            e: FirebaseException
                        ) {

                            onVerificationFailed(
                                e.message
                                    ?: "Phone verification failed."
                            )
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider
                            .ForceResendingToken
                        ) {

                            onCodeSent(
                                verificationId
                            )
                        }
                    }
                )
                .build()

        PhoneAuthProvider
            .verifyPhoneNumber(options)
    }
    suspend fun getStreak(): Result<Int> {

        return try {

            val user = auth.currentUser
                ?: throw Exception("User not logged in.")

            val document = firestore
                .collection("users")
                .document(user.uid)
                .get()
                .await()

            val streak =
                document.getLong("streak")?.toInt() ?: 0

            Result.success(streak)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    // =========================================================
    // VERIFY PHONE OTP
    // =========================================================

    suspend fun verifyPhoneOtp(
        verificationId: String,
        otp: String
    ): Result<String> {

        return try {

            val credential =
                PhoneAuthProvider.getCredential(
                    verificationId,
                    otp
                )

            val currentUser =
                auth.currentUser
                    ?: throw Exception(
                        "User account not found."
                    )

            currentUser
                .linkWithCredential(
                    credential
                )
                .await()

            Result.success(
                currentUser.uid
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
// =========================================================
// UPDATE PRACTICE STREAK
// =========================================================

    suspend fun updatePracticeStreak(): Result<Int> {

        return try {

            val user = auth.currentUser
                ?: throw Exception("User not logged in.")

            val userRef = firestore
                .collection("users")
                .document(user.uid)

            val snapshot = userRef
                .get()
                .await()

            val currentStreak =
                snapshot.getLong("streak")?.toInt() ?: 0

            val lastPracticeDate =
                snapshot.getString("lastPracticeDate")

            val today =
                java.text.SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).format(java.util.Date())

            // Already practiced today
            if (lastPracticeDate == today) {
                return Result.success(currentStreak)
            }

            val newStreak = if (lastPracticeDate == null) {

                // First practice
                1

            } else {

                val formatter =
                    java.text.SimpleDateFormat(
                        "yyyy-MM-dd",
                        java.util.Locale.getDefault()
                    )

                val lastDate =
                    formatter.parse(lastPracticeDate)

                val todayDate =
                    formatter.parse(today)

                val difference =
                    (todayDate.time - lastDate.time) /
                            (1000L * 60 * 60 * 24)

                if (difference == 1L) {
                    currentStreak + 1
                } else {
                    1
                }
            }

            userRef.update(
                mapOf(
                    "streak" to newStreak,
                    "lastPracticeDate" to today
                )
            ).await()

            Result.success(newStreak)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    // =========================================================
    // CHECK LOGIN STATUS
    // =========================================================

    fun isUserLoggedIn(): Boolean {

        return auth.currentUser != null
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    fun logout() {

        auth.signOut()
    }
}