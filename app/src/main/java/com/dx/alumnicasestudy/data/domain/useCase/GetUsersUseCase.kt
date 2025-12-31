package com.dx.alumnicasestudy.data.domain.useCase

import com.dx.alumnicasestudy.data.domain.util.OrderBy
import com.dx.alumnicasestudy.data.domain.util.OrderType
import jakarta.inject.Inject

class GetUsersUseCase @Inject constructor(
//    private val repo: UserRepository (note we need to save the user to the firestore)
) {
    operator fun invoke(
        orderBy: OrderBy = OrderBy.TechStack(OrderType.Ascending)
    ) = safeFlow {
//        val users = repo.getUsers()
        when(orderBy.orderType) {
            OrderType.Ascending -> {
                when(orderBy) {
                    is OrderBy.TechStack -> {}// users.sortedBy { it.primary_stack }
                    is OrderBy.Location -> {}// users.sortedBy { it.city/country }
                    is OrderBy.Graduation -> {}// users.sortedBy { it.graduation_year }
                }
            }
            OrderType.Descending -> {
                when(orderBy) {
                    is OrderBy.TechStack -> {}// users.sortedBy { it.primary_stack }
                    is OrderBy.Location -> {}// users.sortedBy { it.city/country }
                    is OrderBy.Graduation -> {}// users.sortedBy { it.graduation_year }
                }
            }
        }
    }
}