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
import com.dx.alumnicasestudy.ui.theme.NavyBlue
import com.dx.alumnicasestudy.ui.theme.OnNavy
import com.dx.alumnicasestudy.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController = rememberNavController(), vm: HomeViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var graduationYear by remember { mutableStateOf<Int?>(null) }
    var department by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showDatePicker by remember { mutableStateOf(false) }

    fun validate(): String? {
        if (name.isBlank()) return "Name required"
        if (email.isBlank()) return "Email required"
        if (password.isBlank()) return "Password required"
        if (password != passwordConfirm) return "Passwords do not match"
        if (graduationYear == null) return "Graduation year required"
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
            // Snackbar host
            SnackbarHost(hostState = snackbarHostState)

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

            // Graduation year selector
            OutlinedTextField(
                value = graduationYear?.toString() ?: "",
                onValueChange = {},
                label = { Text("Graduation Year") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                trailingIcon = {
                    Button(onClick = { showDatePicker = true }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy), shape = RoundedCornerShape(8.dp)) { Text("Select") }
                }
            )
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
                        scope.launch { snackbarHostState.showSnackbar(err) }
                    } else {
                        vm.register(
                            name = name,
                            email = email,
                            password = password,
                            graduationYear = graduationYear ?: 0,
                            department = department,
                            jobTitle = jobTitle,
                            company = company
                        ) { route ->
                            scope.launch { snackbarHostState.showSnackbar("Registration submitted") }
                            navController.navigate(route)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !vm.isLoading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy)
            ) {
                Text(if (vm.isLoading) "Submitting..." else "Sign up")
            }
            Spacer(Modifier.height(16.dp))
            // Place login navigation on its own line below
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Have an account already?")
                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    scope.launch { snackbarHostState.showSnackbar("Navigate to login") }
                    navController.navigate(Screens.Login.route)
                }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy), shape = RoundedCornerShape(8.dp)) { Text("Sign In!") }
            }
        }

        // Show error codes via snackbar
        LaunchedEffect(vm.errorMessage) {
            vm.errorMessage?.let { msg ->
                scope.launch { snackbarHostState.showSnackbar(msg) }
            }
        }

        if (showDatePicker) {
            // Material Date Picker for selecting year only (simplified by picking any date and taking its year)
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val year = java.util.Calendar.getInstance().apply { timeInMillis = millis }.get(java.util.Calendar.YEAR)
                            graduationYear = year
                            scope.launch { snackbarHostState.showSnackbar("Selected year: $year") }
                        }
                        showDatePicker = false
                    }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy), shape = RoundedCornerShape(8.dp)) { Text("OK") }
                },
                dismissButton = {
                    Button(onClick = { showDatePicker = false }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy), shape = RoundedCornerShape(8.dp)) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}