package com.abhishek.englishteacher.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.abhishek.englishteacher.utils.NetworkMonitor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.englishteacher.repository.GeminiRepository
import kotlinx.coroutines.launch
enum class LearningMode {
    ENGLISH_GRAMMAR,
    HINDI_TO_ENGLISH
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val networkMonitor = NetworkMonitor(application)
    private val repository = GeminiRepository(networkMonitor)
    var learningMode by mutableStateOf(
        LearningMode.ENGLISH_GRAMMAR
    )
        private set

    fun changeLearningMode(
        mode: LearningMode
    ) {
        learningMode = mode
    }

    var userSentence by mutableStateOf("")
        private set

    var correctedSentence by mutableStateOf("")
        private set

    var explanation by mutableStateOf("")
        private set

    var isThinking by mutableStateOf(false)
        private set
    var isListening by mutableStateOf(false)
        private set

    fun startListening() {
        isListening = true
    }

    fun stopListening() {
        isListening = false
    }

    fun processSentence(sentence: String) {

        userSentence = sentence

        isThinking = true
        viewModelScope.launch {

            try {
                println("Selected Mode = $learningMode")
                val result =
                    repository.processSentence(
                        sentence = sentence,
                        mode = if (
                            learningMode == LearningMode.ENGLISH_GRAMMAR
                        ) {
                            "ENGLISH"
                        } else {
                            "HINDI"
                        }
                    )

                correctedSentence = result.first
                explanation = result.second

            } catch (e: Exception) {

                correctedSentence = ""
                explanation = e.stackTraceToString()

            }

            isThinking = false
        }

    }
}