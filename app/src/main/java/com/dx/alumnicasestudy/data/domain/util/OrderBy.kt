package com.dx.alumnicasestudy.data.domain.util

sealed class OrderBy(val orderType: OrderType) {
    class TechStack(orderType: OrderType): OrderBy(orderType)
    class Location(orderType: OrderType): OrderBy(orderType)
    class Graduation(orderType: OrderType): OrderBy(orderType)
    class Name(orderType: OrderType): OrderBy(orderType)

    fun copy(orderType: OrderType): OrderBy {
        return when(this) {
            is Name -> Name(orderType)
            is TechStack -> TechStack(orderType)
            is Location -> Location(orderType)
            is Graduation -> Graduation(orderType)
        }
    }
}