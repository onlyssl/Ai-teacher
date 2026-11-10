package com.abhishek.englishteacher.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TextToSpeechManager(
    context: Context
) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech =
        TextToSpeech(context, this)

    private var isReady = false

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            textToSpeech.language = Locale.US
            textToSpeech.setSpeechRate(0.9f)
            textToSpeech.setPitch(1.0f)

            isReady = true
        }
    }

    fun speak(text: String) {

        if (!isReady) return

        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "emma_voice"
        )
    }

    fun stop() {
        textToSpeech.stop()
    }

    fun release() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}