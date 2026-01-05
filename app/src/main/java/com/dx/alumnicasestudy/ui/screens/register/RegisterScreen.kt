package com.dx.alumnicasestudy.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel

@Composable
fun RegisterScreen(navController: NavController = rememberNavController(), vm: HomeViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var graduationYear by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }

    fun validate(): String? {
        if (name.isBlank()) return "Name required"
        if (email.isBlank()) return "Email required"
        if (password.isBlank()) return "Password required"
        if (password != passwordConfirm) return "Passwords do not match"
        if (graduationYear.isBlank()) return "Graduation year required"
        if (department.isBlank()) return "Department required"
        if (jobTitle.isBlank()) return "Job title required"
        if (company.isBlank()) return "Company required"
        return null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Register", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Fullname") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), visualTransformation = PasswordVisualTransformation())
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = passwordConfirm, onValueChange = { passwordConfirm = it }, label = { Text("Confirm Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), visualTransformation = PasswordVisualTransformation())
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = graduationYear, onValueChange = { if (it.all { c -> c.isDigit() }) graduationYear = it }, label = { Text("Graduation Year") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = department, onValueChange = { department = it }, label = { Text("Department") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = jobTitle, onValueChange = { jobTitle = it }, label = { Text("Job Title") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("Company") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp))
            Spacer(Modifier.height(16.dp))
            if (vm.errorMessage != null) {
                Text(vm.errorMessage!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            Button(
                onClick = {
                    val err = validate()
                    if (err != null) {
                        vm.errorMessage = err
                    } else {
                        vm.register(
                            name = name,
                            email = email,
                            password = password,
                            graduationYear = graduationYear.toIntOrNull() ?: 0,
                            department = department,
                            jobTitle = jobTitle,
                            company = company
                        ) { route ->
                            navController.navigate(route)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !vm.isLoading,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (vm.isLoading) "Submitting..." else "Sign up")
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("Have an account already?")
                TextButton(onClick = { navController.navigate(Screens.Login.route) }) { Text("Sign In!") }
            }
        }
    }
}