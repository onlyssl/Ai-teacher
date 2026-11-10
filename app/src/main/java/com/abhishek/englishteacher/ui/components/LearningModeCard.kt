package com.abhishek.englishteacher.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.abhishek.englishteacher.viewmodel.LearningMode


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningModeCard(
    selectedMode: LearningMode,
    onModeSelected: (LearningMode) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(

        expanded = expanded,

        onExpandedChange = {
            expanded = !expanded
        }

    ) {

        OutlinedTextField(

            value = when (selectedMode) {

                LearningMode.ENGLISH_GRAMMAR ->
                    "🇬🇧 English Grammar"

                LearningMode.HINDI_TO_ENGLISH ->
                    "🇮🇳 Hindi → English"
            },

            onValueChange = {},

            readOnly = true,

            label = {

                Text(
                    text = "Learning Mode"
                )
            },

            trailingIcon = {

                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),

            colors = OutlinedTextFieldDefaults.colors(

                // Main selected text
                focusedTextColor =
                    Color.White,

                unfocusedTextColor =
                    Color.White,

                // Label
                focusedLabelColor =
                    Color(0xFFB58CFF),

                unfocusedLabelColor =
                    Color(0xFFB8B9C9),

                // Border
                focusedBorderColor =
                    Color(0xFF9C6CFF),

                unfocusedBorderColor =
                    Color(0xFF777A91),

                // Dropdown arrow
                focusedTrailingIconColor =
                    Color(0xFFB58CFF),

                unfocusedTrailingIconColor =
                    Color(0xFFB8B9C9),

                // Background
                focusedContainerColor =
                    Color(0xFF10132D),

                unfocusedContainerColor =
                    Color(0xFF10132D),

                // Cursor
                cursorColor =
                    Color(0xFFB58CFF)
            )
        )


        DropdownMenu(

            expanded = expanded,

            onDismissRequest = {
                expanded = false
            },

            containerColor =
                Color(0xFF1C1F3A)

        ) {

            // ==============================================
            // ENGLISH GRAMMAR
            // ==============================================

            DropdownMenuItem(

                text = {

                    Text(
                        text =
                            "🇬🇧 English Grammar",

                        color =
                            Color.White
                    )
                },

                onClick = {

                    onModeSelected(
                        LearningMode.ENGLISH_GRAMMAR
                    )

                    expanded = false
                }
            )


            // ==============================================
            // HINDI TO ENGLISH
            // ==============================================

            DropdownMenuItem(

                text = {

                    Text(
                        text =
                            "🇮🇳 Hindi → English",

                        color =
                            Color.White
                    )
                },

                onClick = {

                    onModeSelected(
                        LearningMode.HINDI_TO_ENGLISH
                    )

                    expanded = false
                }
            )
        }
    }
}