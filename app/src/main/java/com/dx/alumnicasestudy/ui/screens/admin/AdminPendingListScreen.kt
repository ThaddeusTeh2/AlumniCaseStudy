package com.dx.alumnicasestudy.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Admin Pending List screen scaffolding
// Purpose:
// - Show users with status = pending
// - Approve action sets status = approved
// - Only accessible if role = admin
// Note: No UI code; placeholder for future implementation.

// this is a dummy remove afterwards
data class PendingUser(val id: Int, val name: String, val status: String)

@Composable
fun AdminPendingListScreen() {
    val pendingUsers = listOf(
        PendingUser(1, "John Doe", "Pending"),
        PendingUser(2, "Jane Doe", "Pending")
    )

    Column(
        Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(Modifier.background(Color.LightGray)) {
            TableCell("Name", weight = .3f, isHeader = true)
            TableCell("Pending", weight = .4f, isHeader = true)
            TableCell("Action", weight = .3f, isHeader = true)
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(pendingUsers) { user ->
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = user.name, weight = .3f)
                    TableCell(text = user.status, weight = .3f)
                    Row(Modifier.weight(.3f).padding(8.dp)) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text("Approve")
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
        fontWeight = if(isHeader) FontWeight.Bold else FontWeight.Normal
    )
}