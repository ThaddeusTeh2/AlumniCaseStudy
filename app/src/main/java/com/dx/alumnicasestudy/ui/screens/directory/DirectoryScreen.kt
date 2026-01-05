package com.dx.alumnicasestudy.ui.screens.directory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(navController: NavController = rememberNavController(), vm: HomeViewModel = HomeViewModel()) {
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(searchText) { vm.loadApproved(searchText) }

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
                    onClick = { navController.navigate(Screens.Profile.route )}
                ) {
                    Icon(Icons.Default.Person, "")
                }
            }
        )
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search alumni...") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(16.dp)) {
            items(vm.approvedUsers) { user ->
                Card(onClick = {}, modifier = Modifier.fillMaxWidth()) {
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