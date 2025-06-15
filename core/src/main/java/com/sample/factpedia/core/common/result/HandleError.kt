package com.sample.factpedia.core.common.result

import android.util.Log
import kotlinx.coroutines.CancellationException
import java.net.SocketTimeoutException

suspend fun <D> handleError(block: suspend () -> D): Response<D, DataError> {
    return try {
        Response.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        val mappedError = when (e) {
            is BadRequestException -> DataError.Network.BAD_REQUEST
            is UnauthorizedException -> DataError.Network.UNAUTHORIZED
            is ForbiddenException -> DataError.Network.FORBIDDEN
            is NotFoundException -> DataError.Network.NOT_FOUND
            is TimeoutException -> DataError.Network.TIMEOUT
            is ConflictException -> DataError.Network.CONFLICT
            is UnavailableException -> DataError.Network.UNAVAILABLE
            is PreconditionFailedException -> DataError.Network.PRECONDITION_FAILED
            is InternalServerException -> DataError.Network.INTERNAL_SERVER
            is SocketTimeoutException -> DataError.Network.TIMEOUT
            is NetworkException -> DataError.Network.INTERNAL_SERVER
            else -> {
                Log.e("handleError", e.stackTraceToString())
                throw NotExpectedException()
            }
        }

        Response.Failure(mappedError)
    }
}