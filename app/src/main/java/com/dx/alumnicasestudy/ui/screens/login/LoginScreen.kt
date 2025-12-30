package com.dx.alumnicasestudy.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@Composable
fun LoginScreen(navController: NavController = rememberNavController(), vm: HomeViewModel = HomeViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        when (route) {
                            "directory" -> navController.navigate("directory")
                            "pending" -> navController.navigate("pending")
                            else -> {}
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Login")
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account?")
                TextButton(onClick = { navController.navigate("register") }) {
                    Text("Sign up!")
                }
            }
        }
    }
}