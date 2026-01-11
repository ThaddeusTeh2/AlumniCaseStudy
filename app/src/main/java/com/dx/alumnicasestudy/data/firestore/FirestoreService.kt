package com.dx.alumnicasestudy.data.firestore

import com.dx.alumnicasestudy.data.domain.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

    suspend fun createUser(user: User): Result<Unit> {
        return try {
            val doc = user.copy(name_lowercase = user.name.lowercase())
            users.document(user.uid).set(doc.toMap(), SetOptions.merge()).await()
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun getUser(uid: String): User? {
        return try {
            val snap = users.document(uid).get().await()
            if (snap.exists()) snap.toUser() else null
        } catch (_: Throwable) {
            null
        }
    }

    suspend fun queryApprovedUsers(namePrefix: String? = null): List<User> {
        return try {
            val base = users.whereEqualTo("status", User.STATUS_APPROVED)
            val snaps = base.get().await()
            val all = snaps.documents.mapNotNull { it.toUser() }
            val prefix = namePrefix?.trim()?.lowercase().orEmpty()
            val filtered = if (prefix.isBlank()) all else all.filter { it.name_lowercase.startsWith(prefix) }
            filtered.sortedBy { it.name_lowercase }
        } catch (_: Throwable) {
            emptyList()
        }
    }

    suspend fun queryRejectedUsers(namePrefix: String? = null): List<User> {
        return try {
            val base = users.whereEqualTo("status", User.STATUS_REJECTED)
            val snaps = base.get().await()
            val all = snaps.documents.mapNotNull { it.toUser() }
            val prefix = namePrefix?.trim()?.lowercase().orEmpty()
            val filtered = if (prefix.isBlank()) all else all.filter { it.name_lowercase.startsWith(prefix) }
            filtered.sortedBy { it.name_lowercase }
        } catch (_: Throwable) {
            emptyList()
        }
    }

    suspend fun queryPendingUsers(): List<User> {
        return try {
            val snaps = users.whereEqualTo("status", User.STATUS_PENDING).get().await()
            snaps.documents.mapNotNull { it.toUser() }.sortedBy { it.created_at }
        } catch (_: Throwable) {
            emptyList()
        }
    }

    suspend fun approveUser(uid: String): Result<Unit> {
        return try {
            users.document(uid).update(mapOf("status" to User.STATUS_APPROVED)).await()
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun rejectUser(uid: String): Result<Unit> {
        return try {
            users.document(uid).update(mapOf("status" to User.STATUS_REJECTED)).await()
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}

private fun User.toMap(): Map<String, Any?> = mapOf(
    "uid" to uid,
    "name" to name,
    "name_lowercase" to name_lowercase,
    "email" to email,
    "graduation_year" to graduation_year,
    "department" to department,
    "job_title" to job_title,
    "company" to company,
    "status" to status,
    "role" to role,
    "created_at" to created_at
)

private fun com.google.firebase.firestore.DocumentSnapshot.toUser(): User? {
    val uid = getString("uid") ?: id
    val name = getString("name") ?: return null
    val email = getString("email") ?: return null
    val graduationYear = getLong("graduation_year")?.toInt() ?: 0
    val department = getString("department") ?: ""
    val jobTitle = getString("job_title") ?: ""
    val company = getString("company") ?: ""
    val status = getString("status") ?: User.STATUS_PENDING
    val role = getString("role") ?: User.ROLE_USER
    val createdAt = getLong("created_at") ?: System.currentTimeMillis()
    return User(
        uid = uid,
        name = name,
        name_lowercase = name.lowercase(),
        email = email,
        graduation_year = graduationYear,
        department = department,
        job_title = jobTitle,
        company = company,
        status = status,
        role = role,
        created_at = createdAt
    )
}
