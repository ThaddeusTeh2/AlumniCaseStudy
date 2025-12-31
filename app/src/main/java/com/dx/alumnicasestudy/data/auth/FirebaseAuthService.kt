package com.dx.alumnicasestudy.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<String?>(auth.currentUser?.uid)
    val authState: StateFlow<String?> = _authState.asStateFlow()

    suspend fun register(email: String, password: String): Result<String> {
        if (email.isBlank() || password.isBlank()) return Result.failure(IllegalArgumentException("Email and password required"))
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(IllegalStateException("No UID returned"))
            _authState.value = uid
            Result.success(uid)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(IllegalStateException("No UID returned"))
            _authState.value = uid
            Result.success(uid)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun signOut() {
        auth.signOut()
        _authState.value = null
    }

    fun currentUid(): String? = auth.currentUser?.uid
}

private suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            cont.resume(task.result as T)
        } else {
            cont.resumeWithException(task.exception ?: RuntimeException("Task failed"))
        }
    }
}