package com.abhishek.englishteacher.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.abhishek.englishteacher.repository.GeminiRepository
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun SpeakingScreen(
    onBackClick: () -> Unit = {}
) {

    val context = LocalContext.current

    // =====================================================
    // TEXT TO SPEECH
    // =====================================================

    var textToSpeech by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    DisposableEffect(Unit) {

        val tts = TextToSpeech(context, null)

        tts.setLanguage(Locale.US)

        textToSpeech = tts

        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    // =====================================================
    // GEMINI
    // =====================================================

    val geminiRepository = remember {
        GeminiRepository()
    }

    val coroutineScope = rememberCoroutineScope()

    var emmaResponse by remember {
        mutableStateOf("")
    }

    var isProcessing by remember {
        mutableStateOf(false)
    }

    // =====================================================
    // SPEECH RECOGNITION
    // =====================================================

    var spokenText by remember {
        mutableStateOf("")
    }

    var isListening by remember {
        mutableStateOf(false)
    }

    var statusText by remember {
        mutableStateOf("Tap the microphone and start speaking")
    }

    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    val recognitionIntent = remember {

        Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.ENGLISH
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en-US"
            )

            putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                true
            )
        }
    }

    // =====================================================
    // SPEECH LISTENER
    // =====================================================

    speechRecognizer.setRecognitionListener(
        object : RecognitionListener {

            override fun onReadyForSpeech(
                params: android.os.Bundle?
            ) {

                isListening = true
                statusText = "Listening..."
            }

            override fun onBeginningOfSpeech() {

                isListening = true
                statusText = "I'm listening..."
            }

            override fun onRmsChanged(
                rmsdB: Float
            ) {
            }

            override fun onBufferReceived(
                buffer: ByteArray?
            ) {
            }

            override fun onEndOfSpeech() {

                isListening = false
                statusText = "Emma is thinking..."
            }

            override fun onError(
                error: Int
            ) {

                isListening = false

                statusText = when (error) {

                    SpeechRecognizer.ERROR_NO_MATCH ->
                        "I couldn't understand that. Please try again."

                    SpeechRecognizer.ERROR_NETWORK ->
                        "Network error. Please check your internet connection."

                    SpeechRecognizer.ERROR_AUDIO ->
                        "Microphone error. Please try again."

                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                        "I didn't hear anything. Please try again."

                    else ->
                        "Something went wrong. Please try again."
                }
            }

            override fun onResults(
                results: android.os.Bundle?
            ) {

                isListening = false

                val matches =
                    results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )

                if (!matches.isNullOrEmpty()) {

                    val userMessage = matches[0]

                    spokenText = userMessage

                    statusText = "Emma is thinking..."

                    isProcessing = true

                    coroutineScope.launch {

                        try {

                            val response =
                                geminiRepository.chatWithEmma(
                                    userMessage = userMessage
                                )

                            emmaResponse = response

                            // =============================================
                            // EMMA SPEAKS
                            // =============================================

                            textToSpeech?.speak(
                                response,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "EmmaResponse"
                            )

                            statusText = "Emma replied!"

                        } catch (e: Exception) {

                            emmaResponse =
                                "Gemini Error: ${e.message ?: "Unknown error"}"

                            statusText =
                                "Gemini request failed"

                        } finally {

                            isProcessing = false
                        }
                    }
                }
            }

            override fun onPartialResults(
                partialResults: android.os.Bundle?
            ) {

                val matches =
                    partialResults?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )

                if (!matches.isNullOrEmpty()) {

                    spokenText = matches[0]
                }
            }

            override fun onEvent(
                eventType: Int,
                params: android.os.Bundle?
            ) {
            }
        }
    )

    // =====================================================
    // MICROPHONE PERMISSION
    // =====================================================

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                speechRecognizer.startListening(
                    recognitionIntent
                )

            } else {

                statusText =
                    "Microphone permission is required."
            }
        }

    // =====================================================
    // START LISTENING
    // =====================================================

    fun startListening() {

        val permission =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            )

        if (
            permission ==
            PackageManager.PERMISSION_GRANTED
        ) {

            speechRecognizer.startListening(
                recognitionIntent
            )

        } else {

            permissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    // =====================================================
    // STOP LISTENING
    // =====================================================

    fun stopListening() {

        speechRecognizer.stopListening()

        isListening = false

        statusText =
            "Tap the microphone and start speaking"
    }

    // =====================================================
    // CLEANUP
    // =====================================================

    DisposableEffect(Unit) {

        onDispose {

            speechRecognizer.destroy()
        }
    }

    // =====================================================
    // MAIN SCREEN
    // =====================================================

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
                .statusBarsPadding()
        ) {

            // =================================================
            // COMPACT TOP BAR
            // =================================================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 16.dp,
                        top = 6.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Speaking with Emma",
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "English conversation practice",
                        color = Color(0xFF9EA1B9),
                        fontSize = 11.sp
                    )
                }
            }

            // =================================================
            // COMPACT EMMA HEADER
            // =================================================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            Color(0xFF8D5CFF)
                                .copy(alpha = 0.18f)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "👩‍🏫",
                        fontSize = 40.sp
                    )
                }

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                Column {

                    Text(
                        text = "Emma",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Your AI English Teacher",
                        color = Color(0xFF9EA1B9),
                        fontSize = 12.sp
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xFF65D98A)
                                )
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = if (isProcessing) {
                                "Thinking..."
                            } else {
                                "Ready to practice"
                            },
                            color = Color(0xFF65D98A),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // =================================================
            // CONVERSATION AREA
            // =================================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 16.dp
                    )
            ) {

                // =================================================
                // EMMA INITIAL MESSAGE
                // =================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF171A38)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Emma says",
                                tint = Color(0xFFB58CFF),
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = "Emma says",
                                color = Color(0xFFB58CFF),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(9.dp)
                        )

                        Text(
                            text = "Hello! 👋 I'm Emma. Let's practice English together. Tell me something about yourself.",
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // =================================================
                // USER RESPONSE
                // =================================================

                if (spokenText.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF171A38)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "You",
                                color = Color(0xFF9EA1B9),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(7.dp)
                            )

                            Text(
                                text = spokenText,
                                color = Color.White,
                                fontSize = 14.sp,
                                lineHeight = 21.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )
                }

                // =================================================
                // EMMA AI RESPONSE
                // =================================================

                if (emmaResponse.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF201A42)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Emma response",
                                    tint = Color(0xFFB58CFF),
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text(
                                    text = "Emma",
                                    color = Color(0xFFB58CFF),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(9.dp)
                            )

                            Text(
                                text = emmaResponse,
                                color = Color.White,
                                fontSize = 14.sp,
                                lineHeight = 21.sp
                            )
                        }
                    }
                }

                // Space for floating microphone
                Spacer(
                    modifier = Modifier.height(110.dp)
                )
            }
        }

        // =====================================================
        // FLOATING MICROPHONE
        // =====================================================

        // =====================================================
// FLOATING MICROPHONE
// =====================================================

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = 140.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(
                        if (isListening) {
                            Color(0xFFE35D6A)
                        } else {
                            Color(0xFF8D5CFF)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {

                IconButton(
                    onClick = {

                        if (isListening) {
                            stopListening()
                        } else {
                            startListening()
                        }
                    },
                    modifier = Modifier.size(68.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription =
                            if (isListening) {
                                "Stop listening"
                            } else {
                                "Start speaking"
                            },
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = if (isListening) {
                    "Listening..."
                } else if (isProcessing) {
                    "Emma is thinking..."
                } else {
                    "Tap to speak"
                },
                color = Color(0xFFB8B9C9),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}