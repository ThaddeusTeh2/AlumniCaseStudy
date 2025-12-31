package com.dx.alumnicasestudy.data.domain.useCase

import jakarta.inject.Inject

class GetUserUseCase @Inject constructor(
//    private val repo: UserRepository (note we need to save the user to the firestore)
) {
    operator fun invoke(userId: String) = safeFlow {
        //repo.getUserDetail(userId)
    }
}