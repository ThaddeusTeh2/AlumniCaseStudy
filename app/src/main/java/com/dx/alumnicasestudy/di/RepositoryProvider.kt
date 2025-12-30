package com.dx.alumnicasestudy.di

import com.dx.alumnicasestudy.data.auth.FirebaseAuthService
import com.dx.alumnicasestudy.data.domain.repo.AuthRepository
import com.dx.alumnicasestudy.data.firestore.FirestoreService

// Simple DI scaffolding / provider
// Purpose:
// - Provide instances of FirebaseAuthService and AuthRepository
// - Wire dependencies for screens/viewmodels
// Note: No implementation, just a placeholder for structure.

object RepositoryProvider {
    // Singletons for in-memory services; swap with real Firebase implementations later
    private val authService: FirebaseAuthService by lazy { FirebaseAuthService() }
    private val firestoreService: FirestoreService by lazy { FirestoreService() }

    val authRepository: AuthRepository by lazy {
        AuthRepository(
            auth = authService,
            store = firestoreService
        )
    }
}
