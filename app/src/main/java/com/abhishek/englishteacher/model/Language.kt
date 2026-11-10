package com.abhishek.englishteacher.model

enum class Language(

    val displayName: String,

    val speechCode: String,

    val localeCode: String

) {

    ENGLISH(
        "English",
        "en-US",
        "en"
    ),

    HINDI(
        "Hindi",
        "hi-IN",
        "hi"
    ),

    FRENCH(
        "French",
        "fr-FR",
        "fr"
    ),

    SPANISH(
        "Spanish",
        "es-ES",
        "es"
    ),

    GERMAN(
        "German",
        "de-DE",
        "de"
    ),

    PUNJABI(
        "Punjabi",
        "pa-IN",
        "pa"
    )

}