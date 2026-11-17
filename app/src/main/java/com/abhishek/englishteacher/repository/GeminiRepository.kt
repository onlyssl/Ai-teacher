package com.abhishek.englishteacher.repository

import com.abhishek.englishteacher.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.delay
import com.abhishek.englishteacher.utils.NetworkMonitor

class GeminiRepository(private val networkMonitor: NetworkMonitor) {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            temperature = 0.2f
        }
    )

    // =====================================================
    // GRAMMAR / TRANSLATION
    // =====================================================

    suspend fun processSentence(
        sentence: String,
        mode: String
    ): Pair<String, String> {

        val prompt =

            if (mode == "ENGLISH") {

                """
You are Emma, an English Grammar Teacher.

IMPORTANT:
- The user is already speaking in English.
- DO NOT translate.
- DO NOT reply in Hindi.
- DO NOT greet the user.
- DO NOT introduce yourself.
- DO NOT say "Namaste".

Your only task is to correct the grammar.

Reply ONLY in this exact format:

Correct:
(correct English sentence)

Explanation:
(simple grammar explanation)

Sentence:
$sentence
""".trimIndent()

            } else {

                """
You are Emma, an expert English speaking coach.

The user will speak in Hindi.

Your job is to:

1. Translate the Hindi sentence into natural spoken English.
2. Give one alternative English sentence if possible.
3. Explain the grammar in very simple English.
4. Encourage the learner with one positive sentence.

Reply ONLY in this format:

Correct:
(English Translation)

Explanation:
(Simple grammar explanation)

Sentence:
$sentence
""".trimIndent()
            }

        val isOnline = networkMonitor.isOnline()

        val text = if (isOnline) {
             val response = generateWithRetry(prompt)
             response.text ?: ""
        } else {
             try {
                 generateOffline(prompt)
             } catch (e: Exception) {
                 e.message ?: ""
             }
        }

        val corrected = text
            .substringAfter("Correct:")
            .substringBefore("Explanation:")
            .trim()

        val explanation = text
            .substringAfter("Explanation:")
            .trim()

        return Pair(corrected, explanation)
    }

    // =====================================================
    // EMMA CONVERSATION
    // =====================================================

    suspend fun chatWithEmma(
        userMessage: String
    ): String {

        val prompt = """
You are Emma, a friendly AI English speaking teacher.

The student is practicing spoken English with you.

Your job is to have a natural English conversation with the student.

Rules:
- Reply naturally to what the student says.
- Do NOT repeat the student's sentence.
- Do NOT translate the student's sentence.
- Do NOT introduce yourself unless the student asks.
- Keep your response short and conversational.
- Ask a simple follow-up question when appropriate.
- If the student's English has a small grammar mistake, respond naturally and gently show the correct form.
- Always reply in English.
- Keep the response suitable for a learner.
- Do not use complicated vocabulary unnecessarily.

Student said:
$userMessage

Emma's response:
""".trimIndent()

        val isOnline = networkMonitor.isOnline()
        return if (isOnline) {
             val response = generateWithRetry(prompt)
             response.text?.trim() ?: "That's interesting! Tell me more."
        } else {
             try {
                 generateOffline(prompt)
             } catch (e: Exception) {
                 e.message ?: "That's interesting! Tell me more."
             }
        }
    }

    // =====================================================
    // OFFLINE GEMINI REQUEST (ML Kit)
    // =====================================================
    
    private suspend fun generateOffline(
        prompt: String
    ): String {
         val generativeModel = com.google.mlkit.genai.prompt.Generation.getClient()
         val request = com.google.mlkit.genai.prompt.GenerateContentRequest.Builder(
               com.google.mlkit.genai.prompt.TextPart(prompt)
         ).build()
         val response = generativeModel.generateContent(request)
         return response.candidates.firstOrNull()?.text ?: ""
    }

    // =====================================================
    // RELIABLE GEMINI REQUEST
    // =====================================================

    private suspend fun generateWithRetry(
        prompt: String
    ): com.google.ai.client.generativeai.type.GenerateContentResponse {

        var lastException: Exception? = null

        repeat(3) { attempt ->

            try {

                return model.generateContent(prompt)

            } catch (e: Exception) {

                lastException = e

                // Wait before trying again.
                // Longer wait after later attempts.
                if (attempt < 2) {

                    delay(
                        when (attempt) {
                            0 -> 1500L
                            else -> 3000L
                        }
                    )
                }
            }
        }

        throw lastException
            ?: Exception("Unable to contact Gemini.")
    }
}