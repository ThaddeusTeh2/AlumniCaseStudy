package com.dx.alumnicasestudy.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.request.ImageRequest
import coil3.compose.AsyncImage
import com.dx.alumnicasestudy.R
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

// Profile screen scaffolding (read-only)
// Purpose:
// - Display basic profile fields for an alumni
// - Email visible only (MVP)
// Note: Placeholder only, no editing.

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: HomeViewModel = HomeViewModel()
) {
    val context = LocalContext.current
    var editMode by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader(
            onBackClick = { navController.popBackStack() },
            editMode = editMode,
            onEditClick = { editMode = !editMode }
        )
        Box(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            if(editMode) {
                Column(
                    Modifier.fillMaxWidth(),
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("email")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("phone")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("LinkedIn, GitHub")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            } else {
                Column(
                    Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
//                            .data()
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.profile_avatar_placeholder),
                            error = painterResource(id = R.drawable.profile_error_placeholder),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text("Name: username", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Contacts:", style = MaterialTheme.typography.titleMedium)
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
}

@Composable
fun ProfileHeader(
    onBackClick: () -> Unit,
    editMode: Boolean,
    onEditClick: () -> Unit
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier.size(24.dp))
            }

            Text(
                "Profile Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onEditClick
            ) {
                Icon(Icons.Default.Edit, "", modifier = Modifier.size(24.dp))
            }
        }
    }
}