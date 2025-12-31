package com.dx.alumnicasestudy.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dx.alumnicasestudy.data.domain.models.User
import com.dx.alumnicasestudy.di.RepositoryProvider
import com.dx.alumnicasestudy.ui.nav.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repo = RepositoryProvider.authRepository
    private val scope = CoroutineScope(Dispatchers.Main)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var currentUser by mutableStateOf<User?>(null)

    var approvedUsers by mutableStateOf<List<User>>(emptyList())
    var pendingUsers by mutableStateOf<List<User>>(emptyList())

    fun login(email: String, password: String, onNavigate: (route: String) -> Unit) {
        isLoading = true
        errorMessage = null
        scope.launch {
            val result = repo.login(email, password)
            result.fold(
                onSuccess = { user: User ->
                    currentUser = user
                    isLoading = false
                    // Admin bypasses pending gate; everyone lands Home hub
                    val next = Screens.Home.route
                    onNavigate(next)
                },
                onFailure = { err: Throwable ->
                    isLoading = false
                    errorMessage = err.message ?: "Login failed"
                }
            )
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        graduationYear: Int,
        department: String,
        jobTitle: String,
        company: String,
        onNavigate: (route: String) -> Unit
    ) {
        isLoading = true
        errorMessage = null
        scope.launch {
            val result = repo.register(
                name = name,
                email = email,
                password = password,
                graduationYear = graduationYear,
                department = department,
                jobTitle = jobTitle,
                company = company
            )
            result.fold(
                onSuccess = { user: User ->
                    currentUser = user
                    isLoading = false
                    // After register, send to Home (will show PendingGate option from there if needed)
                    onNavigate(Screens.Home.route)
                },
                onFailure = { err: Throwable ->
                    isLoading = false
                    errorMessage = err.message ?: "Registration failed"
                }
            )
        }
    }

    fun loadApproved(namePrefix: String? = null) {
        isLoading = true
        scope.launch {
            try {
                approvedUsers = repo.loadApprovedUsers(namePrefix)
                isLoading = false
            } catch (err: Throwable) {
                isLoading = false
                errorMessage = err.message ?: "Failed to load approved users"
            }
        }
    }

    fun loadPending() {
        isLoading = true
        scope.launch {
            try {
                pendingUsers = repo.loadPendingUsers()
                isLoading = false
            } catch (err: Throwable) {
                isLoading = false
                errorMessage = err.message ?: "Failed to load pending users"
            }
        }
    }

    fun approveUser(uid: String) {
        isLoading = true
        scope.launch {
            val result = repo.approveUser(uid)
            result.fold(
                onSuccess = {
                    loadPending()
                    isLoading = false
                },
                onFailure = { err: Throwable ->
                    isLoading = false
                    errorMessage = err.message ?: "Failed to approve user"
                }
            )
        }
    }

    fun signOut(onNavigate: (route: String) -> Unit) {
        scope.launch {
            repo.signOut()
            currentUser = null
            onNavigate("login")
        }
    }
}
