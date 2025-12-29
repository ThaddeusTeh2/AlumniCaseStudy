package com.dx.alumnicasestudy.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Profile screen scaffolding (read-only)
// Purpose:
// - Display basic profile fields for an alumni
// - Email visible only (MVP)
// Note: Placeholder only, no editing.

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader(
            onBackClick = { navController.popBackStack() }
        )
        Box(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            Column(
                Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {  }
                Text("Name: username", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Contacts:", style= MaterialTheme.typography.titleMedium)
                Row(
                  modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("email,")
                    Spacer(Modifier.width(4.dp))
                    Text("phone (optional),")
                    Spacer(Modifier.width(4.dp))
                    Text("etc...,")
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    onBackClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick },
                modifier = Modifier.size(48.dp)
            ) {
                //Icon(Icons.Default.ArrowBack, "", modifier = Modifier.size(24.dp))
            }

            Spacer(Modifier.width(12.dp))

            Text(
                "Profile Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}