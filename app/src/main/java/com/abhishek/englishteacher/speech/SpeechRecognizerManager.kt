package com.abhishek.englishteacher.speech

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat

class SpeechRecognizerManager(
    private val context: Context
) {

    private var speechRecognizer: SpeechRecognizer? = null

    fun startListening(
        onResult: (String) -> Unit,
        onListening: (Boolean) -> Unit
    ) {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        speechRecognizer?.destroy()

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        speechRecognizer?.setRecognitionListener(
            object : RecognitionListener {

                override fun onReadyForSpeech(params: android.os.Bundle?) {
                    onListening(true)
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    onListening(false)
                }

                override fun onError(error: Int) {
                    onListening(false)
                }

                override fun onResults(results: android.os.Bundle?) {

                    val text =
                        results
                            ?.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION
                            )
                            ?.firstOrNull()

                    if (text != null) {
                        onResult(text)
                    }

                }

                override fun onPartialResults(partialResults: android.os.Bundle?) {}

                override fun onEvent(
                    eventType: Int,
                    params: android.os.Bundle?
                ) {}
            }
        )

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "en-US"
            )

            putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                true
            )

        }

        speechRecognizer?.startListening(intent)

    }

    fun stopListening() {
        speechRecognizer?.stopListening()
    }

    fun destroy() {
        speechRecognizer?.destroy()
    }

}