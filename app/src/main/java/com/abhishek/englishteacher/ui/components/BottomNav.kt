package com.abhishek.englishteacher.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNav(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {

    NavigationBar(
        containerColor = Color(0xFF151933)
    ) {

        // HOME
        NavigationBarItem(
            selected = selectedItem == 0,

            onClick = {
                onItemSelected(0)
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },

            label = {
                Text(
                    text = "Home"
                )
            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFF19152F),

                selectedTextColor = Color.White,

                indicatorColor = Color(0xFFE8D9FF),

                unselectedIconColor = Color(0xFFB8B9C9),

                unselectedTextColor = Color(0xFFB8B9C9)
            )
        )


        // PRACTICE
        NavigationBarItem(
            selected = selectedItem == 1,

            onClick = {
                onItemSelected(1)
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Practice"
                )
            },

            label = {
                Text(
                    text = "Practice"
                )
            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFF19152F),

                selectedTextColor = Color.White,

                indicatorColor = Color(0xFFE8D9FF),

                unselectedIconColor = Color(0xFFB8B9C9),

                unselectedTextColor = Color(0xFFB8B9C9)
            )
        )


        // HISTORY
        NavigationBarItem(
            selected = selectedItem == 2,

            onClick = {
                onItemSelected(2)
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History"
                )
            },

            label = {
                Text(
                    text = "History"
                )
            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFF19152F),

                selectedTextColor = Color.White,

                indicatorColor = Color(0xFFE8D9FF),

                unselectedIconColor = Color(0xFFB8B9C9),

                unselectedTextColor = Color(0xFFB8B9C9)
            )
        )


        // PROFILE
        NavigationBarItem(
            selected = selectedItem == 3,

            onClick = {
                onItemSelected(3)
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },

            label = {
                Text(
                    text = "Profile"
                )
            },

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color(0xFF19152F),

                selectedTextColor = Color.White,

                indicatorColor = Color(0xFFE8D9FF),

                unselectedIconColor = Color(0xFFB8B9C9),

                unselectedTextColor = Color(0xFFB8B9C9)
            )
        )
    }
}