package com.dx.alumnicasestudy.data.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseAuthService {
    // Placeholder service for Firebase Authentication interactions
    // Responsibilities:
    // - Initialize Firebase Auth
    // - Email/password sign-up
    // - Email/password login
    // - Sign-out
    // - Get current user UID
    // - Observe auth state (optional)
    // No concrete implementation; this is structural scaffolding.

    // Assert: Below is a lightweight, dependency-free implementation that mimics FirebaseAuth using in-memory storage.

    private val usersByEmail = mutableMapOf<String, Pair<String /*uid*/, String /*password*/>>()
    private val _authState = MutableStateFlow<String?>(null) // current uid or null

    val authState: StateFlow<String?> = _authState.asStateFlow()

    suspend fun register(email: String, password: String): Result<String /*uid*/> {
        if (email.isBlank() || password.isBlank()) return Result.failure(IllegalArgumentException("Email and password required"))
        if (usersByEmail.containsKey(email)) return Result.failure(IllegalStateException("Email already registered"))
        val uid = generateUid(email)
        usersByEmail[email] = uid to password
        _authState.value = uid
        return Result.success(uid)
    }

    suspend fun login(email: String, password: String): Result<String /*uid*/> {
        val record = usersByEmail[email] ?: return Result.failure(IllegalArgumentException("No account for email"))
        if (record.second != password) return Result.failure(IllegalArgumentException("Invalid credentials"))
        _authState.value = record.first
        return Result.success(record.first)
    }

    suspend fun signOut() {
        _authState.value = null
    }

    fun currentUid(): String? = _authState.value

    private fun generateUid(seed: String): String {
        // Deterministic-ish uid for simplicity; in real Firebase, this is provided.
        return "uid_" + seed.lowercase().hashCode().toString()
    }
}