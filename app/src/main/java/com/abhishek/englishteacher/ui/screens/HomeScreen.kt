package com.abhishek.englishteacher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.abhishek.englishteacher.audio.SoundManager
import com.abhishek.englishteacher.audio.ThinkingMusicManager
import com.abhishek.englishteacher.speech.SpeechRecognizerManager
import com.abhishek.englishteacher.tts.TextToSpeechManager

import com.abhishek.englishteacher.ui.components.BottomNav
import com.abhishek.englishteacher.ui.components.HeroSection
import com.abhishek.englishteacher.ui.components.LearningModeCard
import com.abhishek.englishteacher.ui.components.MicCard
import com.abhishek.englishteacher.ui.components.ResultCard
import com.abhishek.englishteacher.ui.components.StatsCard

import com.abhishek.englishteacher.ui.theme.Background

import com.abhishek.englishteacher.viewmodel.AuthViewModel
import com.abhishek.englishteacher.viewmodel.HomeViewModel

import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {

    val homeViewModel: HomeViewModel = viewModel()

    val authViewModel: AuthViewModel = viewModel()


    // =========================================================
    // NAVIGATION STATE
    // =========================================================

    var selectedItem by remember {
        mutableStateOf(0)
    }
    var showSpeaking by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()


    // =========================================================
    // LOAD USER
    // =========================================================

    LaunchedEffect(Unit) {
        authViewModel.loadUserName()
        authViewModel.loadStreak()
    }


    // =========================================================
    // CONTEXT
    // =========================================================

    val context = LocalContext.current


    val speechManager = remember {
        SpeechRecognizerManager(context)
    }

    val ttsManager = remember {
        TextToSpeechManager(context)
    }

    val soundManager = remember {
        SoundManager(context)
    }

    val thinkingMusic = remember {
        ThinkingMusicManager(context)
    }


    // =========================================================
    // SPEAK CORRECTED SENTENCE
    // =========================================================

    LaunchedEffect(
        homeViewModel.correctedSentence
    ) {

        if (
            homeViewModel
                .correctedSentence
                .isNotBlank()
        ) {

            ttsManager.speak(
                homeViewModel.correctedSentence
            )
        }
    }


    // =========================================================
    // THINKING MUSIC
    // =========================================================

    LaunchedEffect(
        homeViewModel.isThinking
    ) {

        if (
            homeViewModel.isThinking
        ) {

            thinkingMusic.start()

        } else {

            thinkingMusic.stop()
        }
    }


    // =========================================================
    // NAVIGATION DRAWER
    // =========================================================

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(

                drawerContainerColor =
                    Color(0xFF151933),

                modifier =
                    Modifier.width(290.dp)

            ) {

                Spacer(
                    modifier =
                        Modifier.height(35.dp)
                )


                // =================================================
                // DRAWER HEADER
                // =================================================

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp
                        )

                ) {

                    Box(

                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                Color(0xFF5951B7),
                                CircleShape
                            ),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                "Profile",

                            tint =
                                Color.White,

                            modifier =
                                Modifier.size(38.dp)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )


                    Text(

                        text =
                            if (
                                authViewModel
                                    .userName
                                    .isBlank()
                            ) {

                                "Welcome!"

                            } else {

                                authViewModel.userName
                            },

                        color =
                            Color.White,

                        fontSize =
                            21.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )


                    Text(

                        text =
                            "Your AI English Coach",

                        color =
                            Color(0xFFB8B9C9),

                        fontSize =
                            12.sp
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(25.dp)
                )


                // =================================================
                // HOME
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.Home,
                            contentDescription =
                                "Home"
                        )
                    },

                    label = {

                        Text(
                            text = "Home"
                        )
                    },

                    selected =
                        selectedItem == 0,

                    onClick = {

                        selectedItem = 0

                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                selectedContainerColor =
                                    Color(0xFFE8D9FF),

                                selectedIconColor =
                                    Color(0xFF19152F),

                                selectedTextColor =
                                    Color(0xFF19152F),

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFB8B9C9),

                                unselectedTextColor =
                                    Color.White
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                // =================================================
                // PRACTICE
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.Mic,
                            contentDescription =
                                "Practice"
                        )
                    },

                    label = {

                        Text(
                            text = "Practice"
                        )
                    },

                    selected =
                        selectedItem == 1,

                    onClick = {

                        selectedItem = 1

                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                selectedContainerColor =
                                    Color(0xFFE8D9FF),

                                selectedIconColor =
                                    Color(0xFF19152F),

                                selectedTextColor =
                                    Color(0xFF19152F),

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFB8B9C9),

                                unselectedTextColor =
                                    Color.White
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                // =================================================
                // HISTORY
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.History,
                            contentDescription =
                                "History"
                        )
                    },

                    label = {

                        Text(
                            text = "History"
                        )
                    },

                    selected =
                        selectedItem == 2,

                    onClick = {

                        selectedItem = 2

                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                selectedContainerColor =
                                    Color(0xFFE8D9FF),

                                selectedIconColor =
                                    Color(0xFF19152F),

                                selectedTextColor =
                                    Color(0xFF19152F),

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFB8B9C9),

                                unselectedTextColor =
                                    Color.White
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                // =================================================
                // PROFILE
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.Person,
                            contentDescription =
                                "Profile"
                        )
                    },

                    label = {

                        Text(
                            text = "Profile"
                        )
                    },

                    selected =
                        selectedItem == 3,

                    onClick = {

                        selectedItem = 3

                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                selectedContainerColor =
                                    Color(0xFFE8D9FF),

                                selectedIconColor =
                                    Color(0xFF19152F),

                                selectedTextColor =
                                    Color(0xFF19152F),

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFB8B9C9),

                                unselectedTextColor =
                                    Color.White
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )


                HorizontalDivider(

                    color =
                        Color(0xFF30334F),

                    modifier =
                        Modifier.padding(
                            horizontal = 20.dp
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )


                // =================================================
                // SETTINGS
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.Settings,
                            contentDescription =
                                "Settings"
                        )
                    },

                    label = {

                        Text(
                            text = "Settings"
                        )
                    },

                    selected = false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFB8B9C9),

                                unselectedTextColor =
                                    Color.White
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )


                // =================================================
                // LOGOUT
                // =================================================

                NavigationDrawerItem(

                    icon = {

                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription =
                                "Logout"
                        )
                    },

                    label = {

                        Text(
                            text = "Logout"
                        )
                    },

                    selected = false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        onLogout()
                    },

                    colors =
                        NavigationDrawerItemDefaults
                            .colors(

                                unselectedContainerColor =
                                    Color.Transparent,

                                unselectedIconColor =
                                    Color(0xFFFF8A9A),

                                unselectedTextColor =
                                    Color(0xFFFF8A9A)
                            ),

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp
                        )
                )
            }
        }

    ) {


        // =========================================================
        // MAIN SCAFFOLD
        // =========================================================

        Scaffold(

            containerColor =
                Background,

            bottomBar = {

                BottomNav(

                    selectedItem =
                        selectedItem,

                    onItemSelected = {

                        selectedItem = it
                    }
                )
            }

        ) { innerPadding ->


            // =====================================================
            // SCREEN SELECTION
            // =====================================================

            when (selectedItem) {


                // =================================================
                // HOME
                // =================================================

                0 -> {

                    Column(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Background
                            )
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .padding(
                                innerPadding
                            )

                    ) {


                        HeroSection(
                            userName = authViewModel.userName,
                            onMenuClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onProfileClick = {
                                selectedItem = 3
                            }
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        LearningModeCard(

                            selectedMode =
                                homeViewModel
                                    .learningMode,

                            onModeSelected = {

                                homeViewModel
                                    .changeLearningMode(it)
                            }
                        )


                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )


                        StatsCard(streak = authViewModel.streak)


                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )


                        MicCard(

                            isListening =
                                homeViewModel
                                    .isListening,

                            isThinking =
                                homeViewModel
                                    .isThinking

                        ) {

                            homeViewModel
                                .startListening()


                            speechManager
                                .startListening(

                                    onResult = { text ->

                                        homeViewModel
                                            .stopListening()

                                        soundManager
                                            .playReceivedSound()

                                        homeViewModel
                                            .processSentence(
                                                text
                                            )
                                    },

                                    onListening = {
                                            listening ->

                                        if (
                                            listening
                                        ) {

                                            homeViewModel
                                                .startListening()

                                        } else {

                                            homeViewModel
                                                .stopListening()
                                        }
                                    }
                                )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(20.dp)
                        )


                        ResultCard(

                            title =
                                "📝 Your Sentence",

                            content =
                                if (
                                    homeViewModel
                                        .userSentence
                                        .isEmpty()
                                ) {

                                    "Tap the microphone and start speaking..."

                                } else {

                                    homeViewModel
                                        .userSentence
                                }
                        )


                        ResultCard(

                            title =
                                "✅ Correct English",

                            content =
                                if (
                                    homeViewModel
                                        .correctedSentence
                                        .isEmpty()
                                ) {

                                    "Your corrected sentence will appear here."

                                } else {

                                    homeViewModel
                                        .correctedSentence
                                }
                        )


                        ResultCard(

                            title =
                                "💡 AI Explanation",

                            content =
                                if (
                                    homeViewModel
                                        .explanation
                                        .isEmpty()
                                ) {

                                    "The AI will explain your mistakes here."

                                } else {

                                    homeViewModel
                                        .explanation
                                }
                        )


                        ResultCard(

                            title =
                                "🔊 AI Pronunciation",

                            content =
                                "Check your pronunciation score and feedback."
                        )


                        Spacer(
                            modifier =
                                Modifier.height(24.dp)
                        )
                    }
                }


                // =================================================
                // PRACTICE
                // =================================================

                1 -> {
                    if (showSpeaking) {
                        SpeakingScreen(
                            onBackClick = {
                                showSpeaking = false
                            }
                        )
                    } else {
                        PracticeScreen(
                            onSpeakingClick = {
                                showSpeaking = true
                            }
                        )
                    }
                }


                // =================================================
                // HISTORY
                // =================================================

                2 -> {

                    HistoryScreen()
                }


                // =================================================
                // PROFILE
                // =================================================

                3 -> {

                    val currentUser =
                        FirebaseAuth
                            .getInstance()
                            .currentUser


                    ProfileScreen(

                        userName =
                            authViewModel.userName,

                        email =
                            currentUser?.email
                                ?: "",

                        onLogout = {

                            onLogout()
                        }
                    )
                }
            }
        }
    }
}