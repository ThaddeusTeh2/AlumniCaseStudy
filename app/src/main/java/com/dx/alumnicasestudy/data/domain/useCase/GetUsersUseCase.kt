package com.dx.alumnicasestudy.data.domain.useCase

import com.dx.alumnicasestudy.data.domain.repo.AuthRepository
import com.dx.alumnicasestudy.data.domain.util.OrderBy
import com.dx.alumnicasestudy.data.domain.util.OrderType
import jakarta.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(
        orderBy: OrderBy = OrderBy.TechStack(OrderType.Ascending)
    ) = safeFlow {
        val users = repo.loadApprovedUsers()
        when(orderBy.orderType) {
            OrderType.Ascending -> {
                when(orderBy) {
                    is OrderBy.TechStack -> { users.sortedBy { it.role } }
                    is OrderBy.Location -> { users.sortedBy { it.company } }
                    is OrderBy.Graduation -> { users.sortedBy { it.graduation_year } }
                }
            }
            OrderType.Descending -> {
                when(orderBy) {
                    is OrderBy.TechStack -> { users.sortedByDescending { it.role } }
                    is OrderBy.Location -> { users.sortedByDescending { it.company } }
                    is OrderBy.Graduation -> { users.sortedByDescending { it.graduation_year } }
                }
            }
        }
    }
}