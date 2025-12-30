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

    fun createUser(user: User): Result<Unit> {
        if (user.uid.isBlank()) return Result.failure(IllegalArgumentException("uid required"))
        users[user.uid] = user.copy(name_lowercase = user.name.lowercase())
        return Result.success(Unit)
    }

    fun getUser(uid: String): User? = users[uid]

    fun queryApprovedUsers(namePrefix: String? = null): List<User> {
        val base = users.values.filter { it.status == "approved" }
        val prefix = namePrefix?.trim()?.lowercase().orEmpty()
        val filtered = if (prefix.isBlank()) base else base.filter { it.name_lowercase.startsWith(prefix) }
        return filtered.sortedBy { it.name_lowercase }
    }

    fun queryPendingUsers(): List<User> = users.values
        .filter { it.status == "pending" }
        .sortedBy { it.created_at }

    fun approveUser(uid: String): Result<Unit> {
        val user = users[uid] ?: return Result.failure(IllegalArgumentException("User not found"))
        users[uid] = user.copy(status = "approved")
        return Result.success(Unit)
    }
}
