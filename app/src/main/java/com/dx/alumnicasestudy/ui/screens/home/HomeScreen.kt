@file:Suppress(
    "FunctionNaming",
    "MaxLineLength",
)

package com.dx.alumnicasestudy.ui.screens.home

// Directory/Home screen scaffolding
// Purpose:
// - Show list of approved alumni (name, graduation year, job title, company)
// - Provide a simple search by name (case-insensitive using name_lowercase)
// Composables to define:
// - @Composable fun DirectoryScreen(...)
// - @Composable fun PendingGateScreen() // shows "Pending admin approval"
// - @Composable fun AdminPendingListScreen(...) // list of users with status=pending, approve action
// ViewModel interactions:
// - Fetch approved users
// - Perform search queries
// - Approve pending users (admin)
// Navigation:
// - Enforce closed network: route to PendingGate if status != approved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// UI-only placeholder for the Alumni Directory screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(
    modifier: Modifier = Modifier,
) {
    // UI-only placeholder: local state for search box, no filtering yet
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Tech Stack Ascend") }

    val sampleAlumni = listOf(
        AlumniRowData("John Tan", "Class of 2021", "Software Engineer", "Grab"),
        AlumniRowData("Aisyah Rahman", "Class of 2020", "Data Analyst", "Petronas"),
        AlumniRowData("Daniel Lim", "Class of 2019", "Product Manager", "Shopee"),
        AlumniRowData("Mei Ling", "Class of 2022", "QA Engineer", "Intel"),
    )

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            TopAppBar(
                title = { Text("Alumni Directory", style = MaterialTheme.typography.titleLarge) }
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search by name") },
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedOption,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("User Filter") },
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                "",
                                modifier = Modifier.clickable { expanded = true }
                            )
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tech Stack Ascend") },
                            onClick = {
                                selectedOption = "Tech Stack Ascend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.TechStack(OrderType.Ascending)
//                                viewModel.getUsers()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Tech Stack Descend") },
                            onClick = {
                                selectedOption = "Tech Stack Descend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.TechStack(OrderType.Descending)
//                                viewModel.getUsers()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Location Ascend") },
                            onClick = {
                                selectedOption = "Location Ascend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.Location(OrderType.Ascending)
//                                viewModel.getUsers()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Location Descend") },
                            onClick = {
                                selectedOption = "Location Descend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.Location(OrderType.Descending)
//                                viewModel.getUsers()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Graduation Ascend") },
                            onClick = {
                                selectedOption = "Graduation Ascend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.Graduation(OrderType.Ascending)
//                                viewModel.getUsers()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Graduation Descend") },
                            onClick = {
                                selectedOption = "Graduation Descend"
                                expanded = false
//                                viewModel.state.value.orderBy = OrderBy.Graduation(OrderType.Descending)
//                                viewModel.getUsers()
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(sampleAlumni) { row ->
                    AlumniRow(row)
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tap a row to view profile",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AlumniRow(row: AlumniRowData) {
    ListItem(
        headlineContent = {
            RowLike(
                left = {
                    Text(
                        row.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                right = {
                    Text(
                        row.graduationYear,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        },
        supportingContent = {
            RowLike(
                left = { Text(row.title, style = MaterialTheme.typography.bodySmall) },
                right = { Text(row.company, style = MaterialTheme.typography.bodySmall) }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* UI-only placeholder: no-op */ },
    )
}

private data class AlumniRowData(
    val name: String,
    val graduationYear: String,
    val title: String,
    val company: String,
)

// Simple two-column row helper to mimic the text layout in the spec
@Composable
private fun RowLike(
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) { left() }
        Column(modifier = Modifier.weight(1f)) { right() }
    }
}

// UI-only placeholder for the Access Restricted (Pending Gate) screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingGateScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = { Text("Access Restricted", style = MaterialTheme.typography.titleLarge) }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Your account is pending admin approval.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Please wait. No further action is required.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Minimal placeholder for Login screen
@Composable
fun LoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login (placeholder)", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Email") })
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Password") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* no-op for now */ }) { Text("Sign In") }
        }
    }
}

// Minimal placeholder for Register screen
@Composable
fun RegisterScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Register (placeholder)", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Name") })
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Email") })
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Password") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* no-op for now */ }) { Text("Create Account") }
        }
    }
}

// Minimal placeholder for Admin Pending List screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPendingListScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = { Text("Admin - Pending Approvals", style = MaterialTheme.typography.titleLarge) }
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "No pending users (placeholder).",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun DirectoryScreenPreview() {
    DirectoryScreen()
}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun PendingGateScreenPreview() {
    PendingGateScreen()
}
