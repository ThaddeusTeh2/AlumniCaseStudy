package com.dx.alumnicasestudy.ui.screens.directory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(navController: NavController, vm: HomeViewModel) {
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Name Ascend") }

    LaunchedEffect(searchText) { vm.loadApproved(searchText) }

    val sortedAlumni = remember(vm.approvedUsers, selectedOption) {
        when(selectedOption) {
            "TechStack Ascend" -> vm.approvedUsers.sortedBy { it.role }
            "TechStack Descend" -> vm.approvedUsers.sortedByDescending { it.role }
            "Location Ascend" -> vm.approvedUsers.sortedBy { it.company }
            "Location Descend" -> vm.approvedUsers.sortedByDescending { it.company }
            "Graduation Ascend" -> vm.approvedUsers.sortedBy { it.graduation_year }
            "Graduation Descend" -> vm.approvedUsers.sortedByDescending { it.graduation_year }
            "Name Ascend" -> vm.approvedUsers.sortedBy { it.name }
            "Name Descend" -> vm.approvedUsers.sortedByDescending { it.name }
            else -> vm.approvedUsers
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        TopAppBar(
            title = { Text("Alumni Directory") },
            actions = {
                IconButton(
                    onClick = { navController.navigate(Screens.MyProfile.route )}
                ) {
                    Icon(Icons.Default.Person, "")
                }
            }
        )
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search alumni...") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text("Alumni filter") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        "",
                        modifier = Modifier.clickable{ expanded = true }
                    )
                },
                modifier = Modifier.weight(1f).clickable{ expanded = true }
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Name Ascend") },
                    onClick = {
                        selectedOption = "Name Ascend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Name Descend") },
                    onClick = {
                        selectedOption = "Name Descend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Graduation Ascend") },
                    onClick = {
                        selectedOption = "Graduation Ascend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Graduation Descend") },
                    onClick = {
                        selectedOption = "Graduation Descend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("TechStack Ascend") },
                    onClick = {
                        selectedOption = "TechStack Ascend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("TechStack Descend") },
                    onClick = {
                        selectedOption = "TechStack Descend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Location Ascend") },
                    onClick = {
                        selectedOption = "Location Ascend"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Location Descend") },
                    onClick = {
                        selectedOption = "Location Descend"
                        expanded = false
                    }
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(16.dp)) {
            items(sortedAlumni) { user ->
                Card(onClick = {
                   navController.navigate(Screens.Profile.createRoute(user.uid))
                }, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(user.name, style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Graduated: ${user.graduation_year}")
                        Spacer(Modifier.height(4.dp))
                        Text("${user.company} • ${user.job_title}")
                    }
                }
            }
        }
    }
}