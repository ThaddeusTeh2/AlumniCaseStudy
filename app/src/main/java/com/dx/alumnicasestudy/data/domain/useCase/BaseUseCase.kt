package com.dx.alumnicasestudy.data.domain.useCase

import coil.network.HttpException
import com.dx.alumnicasestudy.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

inline fun <T>safeFlow(crossinline block: suspend () -> T): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading())
        val result = block()
        emit(Resource.Success(result))
    } catch (e: HttpException) {
        emit(Resource.Error(e.localizedMessage ?: "Something went wrong."))
    } catch (_: IOException) {
        emit(Resource.Error("Couldn't reach the server."))
    } catch (e: Exception) {
        emit(Resource.Error("Don't know what happened"))
    }
}