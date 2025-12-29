package com.dx.alumnicasestudy.ui.screens.directory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Directory screen scaffolding
// Purpose:
// - Show list of approved alumni: name, graduation year, job title, company
// - Search by name using name_lowercase
// - Read-only profile view (basic)
// Note: Placeholder for UI composition.

@Composable
fun DirectoryScreen() {

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search alumni...") },
//                leadingIcon = {
//                    Icon(Icons.Default.Search, contentDescription = "")
//                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {} //navigate to the users profile
            ) {
//                Icon(Icons.Default.Person, contentDescription = "")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
//            items(users) {
//                Card(
//                    elevation = CardDefaults.cardElevation(4.dp),
//                    modifier = Modifier.clickable{}
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(16.dp)
//                            ) {
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.SpaceAround
//                                ) {
//                                    Text("Name here (placeholder)", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                                    Text("Graduated: 2005 (placeholder)", style = MaterialTheme.typography.titleLarge)
//                                }
//                                Spacer(Modifier.height(20.dp))
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                                ) {
//                                    Text("Company name (Placeholder)", style = MaterialTheme.typography.titleMedium)
//                                    Text("•", fontSize = 14.sp)
//                                    Text("Title: Part-Timer/Staff (Placeholder)", style = MaterialTheme.typography.titleMedium)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}