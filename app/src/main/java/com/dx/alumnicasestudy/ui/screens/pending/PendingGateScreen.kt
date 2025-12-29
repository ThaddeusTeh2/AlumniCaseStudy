package com.dx.alumnicasestudy.ui.screens.pending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Pending Gate screen scaffolding
// Purpose:
// - Show static message: "Pending admin approval"
// - Block access to directory until status = approved
// Note: Placeholder only.

@Composable
fun PendingGateScreen() {
    Box(
        Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //if(user.pending) {
            Text("Your account is pending admin approval.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        } else {
//            Text("Your registration was not approved. Contact admin if needed.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        }
            Spacer(Modifier.height(16.dp))
            Text("If this screen persists, please contact to the admin.")
        }
    }
}