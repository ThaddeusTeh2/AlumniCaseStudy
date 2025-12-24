package com.dx.alumnicasestudy.ui.screens.register

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

@Composable
fun RegisterScreen() {
    // MVP Registration requirements:
    // Required fields only: name, email, graduation_year, department, job_title, company, password
    // Remove out-of-scope fields (domain, city/country, contactPref, etc.) for MVP
    // On Sign up:
    // - Call AuthRepository.register(...)
    // - Create Firebase Auth user
    // - Create Firestore users/{uid} with status="pending" and role="user"
    // - Navigate to PendingGate screen after successful registration
    // "Sign In!" navigates back to Login screen

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("")}
    var graduationYear by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var currentJobPosition by remember { mutableStateOf("") }
    var currentCompany by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("") }
    var currentCityCountry by remember { mutableStateOf("") }
    var contactPref by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Register here", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label ={ Text("Fullname") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label ={ Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = pass,
                onValueChange = {pass = it},
                label ={ Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = passwordConfirm,
                onValueChange = {passwordConfirm = it},
                label ={ Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = graduationYear,
                onValueChange = {
                        if(it.all { char -> char.isDigit() } ) {
                            graduationYear = it
                        }
                                },
                label ={ Text("Graduation Year") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = department,
                onValueChange = {department = it},
                label ={ Text("Department") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = currentJobPosition,
                onValueChange = {currentJobPosition = it},
                label ={ Text("Jon Position") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = currentCompany,
                onValueChange = {currentCompany = it},
                label ={ Text("Company") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = domain,
                onValueChange = {domain = it},
                label ={ Text("Domain") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = currentCityCountry,
                onValueChange = {currentCityCountry = it},
                label ={ Text("City, Country") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = contactPref,
                onValueChange = {contactPref = it},
                label ={ Text("Preference") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign up")
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Have an account already?")
                TextButton(
                    onClick = {}
                ) {
                    "Sign In!"
                }
            }
        }
    }
}