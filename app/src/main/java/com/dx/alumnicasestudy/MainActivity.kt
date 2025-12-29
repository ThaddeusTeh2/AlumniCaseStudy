package com.dx.alumnicasestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.dx.alumnicasestudy.ui.theme.AlumniCaseStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlumniCaseStudyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // val innerPadding = it // Content padding; will be used when NavHost is added
                    // Navigation scaffolding to be added:
                    // - NavHost with routes from ui.nav.Screens
                    // - Start destination: Login
                    // - After login, route to Directory or PendingGate based on Firestore user status
                    // - Admin route to AdminPendingList if role=admin
                    // Note: No functional code added here yet.
                }
            }
        }
    }
}
