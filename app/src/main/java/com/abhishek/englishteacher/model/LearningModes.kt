package com.abhishek.englishteacher.model

object LearningModes {

    val EnglishGrammar = LearningMode(

        sourceLanguage = Language.ENGLISH,

        targetLanguage = Language.ENGLISH,

        modeType = ModeType.GRAMMAR

    )

    val HindiToEnglish = LearningMode(

        sourceLanguage = Language.HINDI,

        targetLanguage = Language.ENGLISH,

        modeType = ModeType.TRANSLATION

    )

    val EnglishToHindi = LearningMode(

        sourceLanguage = Language.ENGLISH,

        targetLanguage = Language.HINDI,

        modeType = ModeType.TRANSLATION

    )

    val HindiToFrench = LearningMode(

        sourceLanguage = Language.HINDI,

        targetLanguage = Language.FRENCH,

        modeType = ModeType.TRANSLATION

    )

    val FrenchToHindi = LearningMode(

        sourceLanguage = Language.FRENCH,

        targetLanguage = Language.HINDI,

        modeType = ModeType.TRANSLATION

    )

    val EnglishConversation = LearningMode(

        sourceLanguage = Language.ENGLISH,

        targetLanguage = Language.ENGLISH,

        modeType = ModeType.CONVERSATION

    )

}