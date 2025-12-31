package com.dx.alumnicasestudy.data.domain.repo

import com.dx.alumnicasestudy.data.auth.FirebaseAuthService
import com.dx.alumnicasestudy.data.domain.models.User
import com.dx.alumnicasestudy.data.firestore.FirestoreService

class AuthRepository(
    private val auth: FirebaseAuthService = FirebaseAuthService(),
    private val store: FirestoreService = FirestoreService()
) {
    // Repository scaffolding for authentication and registration
    // Responsibilities:
    // - Coordinate between FirebaseAuthService and Firestore
    // - Sign-up: create Auth user and Firestore users/{uid} doc with MVP fields
    // - Login: authenticate and fetch user profile/status/role
    // - Approve: for admin, update status to "approved"

    // Methods to define:
    // - suspend fun register(name, email, password, graduationYear, department, jobTitle, company): Result<...>
    // - suspend fun login(email, password): Result<...>
    // - suspend fun getCurrentUserProfile(): User?
    // - suspend fun approveUser(uid: String): Result<...>
    // - fun isAdmin(user): Boolean
    // Note: Leaving implementation empty for structure visualization.

    // Assert: Below are minimal implementations using in-memory services to validate flow without Firebase deps.

    suspend fun register(
        name: String,
        email: String,
        password: String,
        graduationYear: Int,
        department: String,
        jobTitle: String,
        company: String
    ): Result<User> {
        val uidResult = auth.register(email, password)
        return uidResult.fold(
            onSuccess = { uid ->
                val user = User.create(
                    uid = uid,
                    name = name,
                    email = email,
                    graduationYear = graduationYear,
                    department = department,
                    jobTitle = jobTitle,
                    company = company,
                    status = User.STATUS_PENDING,
                    role = User.ROLE_USER
                )
                store.createUser(user).fold(
                    onSuccess = { Result.success(user) },
                    onFailure = { Result.failure(it) }
                )
            },
            onFailure = { Result.failure(it) }
        )
    }

    suspend fun login(email: String, password: String): Result<User> {
        return auth.login(email, password).fold(
            onSuccess = { uid ->
                val user = store.getUser(uid)
                if (user != null) Result.success(user) else Result.failure(IllegalStateException("Profile not found"))
            },
            onFailure = { Result.failure(it) }
        )
    }

    suspend fun signOut() { auth.signOut() }

    suspend fun loadApprovedUsers(namePrefix: String? = null): List<User> = store.queryApprovedUsers(namePrefix)

    suspend fun loadPendingUsers(): List<User> = store.queryPendingUsers()

    suspend fun approveUser(uid: String): Result<Unit> = store.approveUser(uid)

    fun isAdmin(user: User?): Boolean = user?.role == User.ROLE_ADMIN
}