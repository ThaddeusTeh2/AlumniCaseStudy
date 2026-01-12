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
import kotlinx.coroutines.launch

@Composable
fun AdminPendingListScreen(vm: HomeViewModel) {
    val isAdmin = vm.currentUser?.isAdmin ?: (vm.currentUser?.role == "admin")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (isAdmin) vm.loadPending()
    }

    Column(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        // Snackbar host for feedback
        SnackbarHost(hostState = snackbarHostState)

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
                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                        // User info section
                        Text(user.name, style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${user.status}")
                        Spacer(Modifier.height(12.dp))
                        // Actions section (stacked below info)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                vm.approveUser(user.uid)
                                scope.launch { snackbarHostState.showSnackbar("Approved ${user.name}") }
                            }) { Text("Approve") }
                            Button(onClick = {
                                vm.rejectUser(user.uid)
                                scope.launch { snackbarHostState.showSnackbar("Rejected ${user.name}") }
                            }) { Text("Reject") }
                        }
                    }
                }
            }
        }
        // Show errors from VM if any
        LaunchedEffect(vm.errorMessage) {
            vm.errorMessage?.let { msg ->
                scope.launch { snackbarHostState.showSnackbar(msg) }
            }
        }
    }
}
