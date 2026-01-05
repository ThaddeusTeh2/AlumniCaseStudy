package com.dx.alumnicasestudy.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@Composable
fun AdminPendingListScreen(vm: HomeViewModel) {
    val isAdmin = vm.currentUser?.isAdmin ?: (vm.currentUser?.role == "admin")

    LaunchedEffect(Unit) {
        if (isAdmin) vm.loadPending()
    }

    Column(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        Row {
            Text("Admin - Pending Approvals", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        if (!isAdmin) {
            Text("You do not have permission to view this page.")
            return@Column
        }
        LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vm.pendingUsers) { user ->
                Card {
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(user.name, style = MaterialTheme.typography.titleMedium)
                            Text("Status: ${user.status}")
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { vm.approveUser(user.uid) }) { Text("Approve") }
                        }
                    }
                }
            }
        }
    }
}
