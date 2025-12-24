package com.dx.alumnicasestudy.data.firestore

import com.dx.alumnicasestudy.data.domain.models.User

// Firestore service scaffolding
// Responsibilities:
// - Create users/{uid} documents on registration
// - Fetch current user profile
// - Query approved users for directory
// - Query pending users for admin
// - Update user status to approved (admin)
// Note: Structure only; no implementation.

// Assert: Below is a simple, dependency-free in-memory implementation that models the above responsibilities.

class FirestoreService {
    private val users = mutableMapOf<String /*uid*/, User>()

    suspend fun createUser(user: User): Result<Unit> {
        if (user.uid.isBlank()) return Result.failure(IllegalArgumentException("uid required"))
        users[user.uid] = user
        return Result.success(Unit)
    }

    suspend fun getUser(uid: String): User? = users[uid]

    suspend fun queryApprovedUsers(): List<User> = users.values.filter { it.status == "approved" }.sortedBy { it.name_lowercase }

    suspend fun queryPendingUsers(): List<User> = users.values.filter { it.status == "pending" }.sortedBy { it.created_at }

    suspend fun approveUser(uid: String): Result<Unit> {
        val user = users[uid] ?: return Result.failure(IllegalArgumentException("User not found"))
        users[uid] = user.copy(status = "approved")
        return Result.success(Unit)
    }
}
