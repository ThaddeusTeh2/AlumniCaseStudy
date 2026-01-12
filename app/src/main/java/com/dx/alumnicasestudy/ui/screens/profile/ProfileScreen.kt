package com.dx.alumnicasestudy.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dx.alumnicasestudy.R
import com.dx.alumnicasestudy.data.domain.models.User
import com.dx.alumnicasestudy.di.RepositoryProvider
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.LazyColumn
import com.dx.alumnicasestudy.ui.nav.Screens
import com.dx.alumnicasestudy.ui.theme.NavyBlue
import com.dx.alumnicasestudy.ui.theme.OnNavy
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape

private const val CARD_WIDTH_FRACTION = 0.95f

// Profile screen scaffolding (read-only)
// Purpose:
// - Display basic profile fields for an alumni
// - Email visible only (MVP)
// Note: Placeholder only, no editing.

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: ProfileViewModel = viewModel(factory = ProfileViewModelFactory()),
    userId: String?
) {
    var editMode by remember { mutableStateOf(false) }

    val user by vm.user.collectAsState()
    val isCurrentUser by vm.isCurrentUser.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(userId) { vm.loadUser(userId) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        // Snackbar host
        SnackbarHost(hostState = snackbarHostState)

        ProfileHeader(
            isCurrentUser = isCurrentUser,
            onBackClick = { navController.popBackStack() },
            onEditClick = { editMode = !editMode },
            onLogoutClick = {
                FirebaseAuth.getInstance().signOut()
                scope.launch { snackbarHostState.showSnackbar("Logged out") }
                navController.navigate(Screens.Login.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                when {
                    user == null -> {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp)) {
                            CircularProgressIndicator()
                        }
                    }
                    editMode && user != null -> {
                        EditProfileForm(
                            user = user!!,
                            onCancel = {
                                editMode = false
                                scope.launch { snackbarHostState.showSnackbar("Edit canceled") }
                            },
                            onSave = { updated ->
                                vm.updateUser(updated) { result ->
                                    if (result.isSuccess) {
                                        editMode = false
                                        scope.launch { snackbarHostState.showSnackbar("Profile updated") }
                                    } else {
                                        scope.launch { snackbarHostState.showSnackbar(result.exceptionOrNull()?.message ?: "Update failed") }
                                    }
                                }
                            }
                        )
                    }
                    else -> {
                        ProfileContent(user = user!!)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    isCurrentUser: Boolean,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Surface(
        color = NavyBlue,
        contentColor = OnNavy,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = OnNavy)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier.size(24.dp))
            }

            Text(
                 if (isCurrentUser) "My Profile" else "Alumni Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnNavy,
                modifier = Modifier.weight(1f)
            )

            if(isCurrentUser) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onEditClick,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = OnNavy)
                    ) {
                        Icon(Icons.Filled.Edit, "", modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.width(4.dp))
                    IconButton(
                        onClick = onLogoutClick,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = OnNavy)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Logout, "", modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContent(user: User) {
    val context = LocalContext.current
    // Centered card containing profile details
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(CARD_WIDTH_FRACTION),
            colors = CardDefaults.cardColors(containerColor = NavyBlue, contentColor = OnNavy)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("") // Placeholder for user's photo URL when available
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.profile_avatar_placeholder),
                        error = painterResource(id = R.drawable.profile_error_placeholder),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnNavy)
                        Text("Graduated: ${user.graduation_year}", style = MaterialTheme.typography.bodyLarge, color = OnNavy)
                    }
                }
                Spacer(Modifier.height(24.dp))
                ProfileInfoRow("Role", user.job_title)
                ProfileInfoRow("Company", user.company)
                ProfileInfoRow("Department", user.department)
                ProfileInfoRow("Email", user.email)
            }
        }
    }
}

@Composable
private fun ButtonDefaultsNavy() = ButtonDefaults.buttonColors(containerColor = NavyBlue, contentColor = OnNavy)

@Composable
private fun EditProfileForm(
    user: User,
    onCancel: () -> Unit,
    onSave: (User) -> Unit,
) {
    var name by remember(user) { mutableStateOf(user.name) }
    var graduation by remember(user) { mutableStateOf(user.graduation_year.toString()) }
    var department by remember(user) { mutableStateOf(user.department) }
    var jobTitle by remember(user) { mutableStateOf(user.job_title) }
    var company by remember(user) { mutableStateOf(user.company) }
    var email by remember(user) { mutableStateOf(user.email) }

    var showDatePicker by remember { mutableStateOf(false) }

    val isValid = remember(name, graduation, department, jobTitle, company, email) {
        name.isNotBlank() && department.isNotBlank() && jobTitle.isNotBlank() && company.isNotBlank() &&
        graduation.toIntOrNull() != null && email.isNotBlank()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(CARD_WIDTH_FRACTION),
            colors = CardDefaults.cardColors(containerColor = NavyBlue, contentColor = OnNavy)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Edit Profile", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = OnNavy)
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                // Graduation year picker (read-only field with select button)
                OutlinedTextField(value = graduation, onValueChange = {}, label = { Text("Graduation Year") }, modifier = Modifier.fillMaxWidth(), readOnly = true,
                    trailingIcon = {
                        Button(onClick = { showDatePicker = true }, colors = ButtonDefaultsNavy(), shape = RoundedCornerShape(8.dp)) { Text("Select") }
                    }
                )
                OutlinedTextField(value = department, onValueChange = { department = it }, label = { Text("Department") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = jobTitle, onValueChange = { jobTitle = it }, label = { Text("Job Title") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("Company") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = onCancel, colors = ButtonDefaultsNavy(), shape = RoundedCornerShape(8.dp)) { Text("Cancel") }
                    Button(
                        enabled = isValid,
                        onClick = {
                            val gradYear = graduation.toIntOrNull() ?: user.graduation_year
                            val updated = user.copy(
                                name = name,
                                graduation_year = gradYear,
                                department = department,
                                job_title = jobTitle,
                                company = company,
                                email = email
                            )
                            onSave(updated)
                        },
                        colors = ButtonDefaultsNavy(),
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Save") }
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val year = java.util.Calendar.getInstance().apply { timeInMillis = millis }.get(java.util.Calendar.YEAR)
                        graduation = year.toString()
                    }
                    showDatePicker = false
                }, colors = ButtonDefaultsNavy(), shape = RoundedCornerShape(8.dp)) { Text("OK") }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }, colors = ButtonDefaultsNavy(), shape = RoundedCornerShape(8.dp)) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = OnNavy
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = OnNavy
        )
    }
}

class ProfileViewModelFactory : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                repo = RepositoryProvider.authRepository,
                auth = FirebaseAuth.getInstance()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
