package com.dx.alumnicasestudy.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.theme.NavyBlue
import com.dx.alumnicasestudy.ui.theme.OnNavy
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController = rememberNavController(), vm: HomeViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.systemBars)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Snackbar host
            SnackbarHost(hostState = snackbarHostState)

            Text("Login", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value= email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value= password,
                onValueChange = { password = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(4.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    vm.login(email, password) { route ->
                        scope.launch { snackbarHostState.showSnackbar("Login successful") }
                        navController.navigate(route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login")
            }
            Spacer(Modifier.height(16.dp))
            // Move navigation prompt and button to their own line below
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Don't have an account?")
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        scope.launch { snackbarHostState.showSnackbar("Navigate to register") }
                        navController.navigate(Screens.Register.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sign up!")
                }
            }
        }

        // Show login error codes via snackbar
        LaunchedEffect(vm.errorMessage) {
            vm.errorMessage?.let { msg ->
                scope.launch { snackbarHostState.showSnackbar(msg) }
            }
        }
    }
}