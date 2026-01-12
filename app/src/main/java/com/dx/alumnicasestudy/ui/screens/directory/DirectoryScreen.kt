package com.dx.alumnicasestudy.ui.screens.directory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.dx.alumnicasestudy.ui.theme.NavyBlue
import com.dx.alumnicasestudy.ui.theme.OnNavy
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(navController: NavController, vm: HomeViewModel) {
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Name Ascend") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { vm.loadApproved() }

    val filteredAlumni = remember(vm.approvedUsers, searchText) {
        val query = searchText.trim().lowercase()
        if (query.isEmpty()) vm.approvedUsers else vm.approvedUsers.filter { user ->
            val matchesName = user.name.lowercase().contains(query)
            val matchesTech = (user.role ?: "").lowercase().contains(query)
            val matchesLocation = (user.company ?: "").lowercase().contains(query)
            val matchesGradYear = user.graduation_year.toString().contains(query)
            matchesName || matchesTech || matchesLocation || matchesGradYear
        }
    }

    val sortedAlumni = remember(filteredAlumni, selectedOption) {
        when(selectedOption) {
            "TechStack Ascend" -> filteredAlumni.sortedBy { it.role }
            "TechStack Descend" -> filteredAlumni.sortedByDescending { it.role }
            "Location Ascend" -> filteredAlumni.sortedBy { it.company }
            "Location Descend" -> filteredAlumni.sortedByDescending { it.company }
            "Graduation Ascend" -> filteredAlumni.sortedBy { it.graduation_year }
            "Graduation Descend" -> filteredAlumni.sortedByDescending { it.graduation_year }
            "Name Ascend" -> filteredAlumni.sortedBy { it.name }
            "Name Descend" -> filteredAlumni.sortedByDescending { it.name }
            else -> filteredAlumni
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
                    onClick = {
                        scope.launch { snackbarHostState.showSnackbar("Opening profile") }
                        navController.navigate(Screens.MyProfile.route )
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = OnNavy)
                ) {
                    Icon(Icons.Default.Person, "")
                }
            }
        )
        // Snackbar host
        SnackbarHost(hostState = snackbarHostState)

        // Filters: sort above search
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                modifier = Modifier.fillMaxWidth()
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
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Name Ascend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Name Descend") },
                    onClick = {
                        selectedOption = "Name Descend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Name Descend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Graduation Ascend") },
                    onClick = {
                        selectedOption = "Graduation Ascend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Graduation Ascend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Graduation Descend") },
                    onClick = {
                        selectedOption = "Graduation Descend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Graduation Descend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("TechStack Ascend") },
                    onClick = {
                        selectedOption = "TechStack Ascend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by TechStack Ascend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("TechStack Descend") },
                    onClick = {
                        selectedOption = "TechStack Descend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by TechStack Descend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Location Ascend") },
                    onClick = {
                        selectedOption = "Location Ascend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Location Ascend") }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Location Descend") },
                    onClick = {
                        selectedOption = "Location Descend"
                        expanded = false
                        scope.launch { snackbarHostState.showSnackbar("Sorted by Location Descend") }
                    }
                )
            }
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search alumni...") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(16.dp)) {
            items(sortedAlumni) { user ->
                Card(onClick = {
                    scope.launch { snackbarHostState.showSnackbar("Viewing ${user.name}") }
                    navController.navigate(Screens.Profile.createRoute(user.uid))
                }, modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = NavyBlue, contentColor = OnNavy)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(user.name, style = MaterialTheme.typography.titleLarge, color = OnNavy)
                        Spacer(Modifier.height(8.dp))
                        Text("Graduated: ${user.graduation_year}", color = OnNavy)
                        Spacer(Modifier.height(4.dp))
                        Text("${user.company} • ${user.job_title}", color = OnNavy)
                    }
                }
            }
        }
    }
}