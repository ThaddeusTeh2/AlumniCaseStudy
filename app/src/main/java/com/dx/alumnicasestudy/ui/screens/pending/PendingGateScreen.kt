package com.dx.alumnicasestudy.ui.screens.pending

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

// Pending Gate screen scaffolding
// Purpose:
// - Show static message: "Pending admin approval"
// - Block access to directory until status = approved
// Note: Placeholder only.

@Composable
fun PendingGateScreen(navController: NavController = rememberNavController(), vm: HomeViewModel = HomeViewModel()) {
    Box(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Your account is pending admin approval.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("You can close the app and await approval, or logout.")
            Spacer(Modifier.height(16.dp))
            if (vm.currentUser?.role == "admin") {
                Button(onClick = { navController.navigate(Screens.Home.route) }) { Text("Go to Home (Admin)") }
                Spacer(Modifier.height(8.dp))
            }
            Button(onClick = { vm.signOut { navController.navigate(Screens.Login.route) } }) {
                Text("Logout")
            }
        }
    }
}