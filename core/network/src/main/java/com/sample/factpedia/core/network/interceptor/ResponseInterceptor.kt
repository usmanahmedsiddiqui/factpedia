package com.sample.factpedia.core.network.interceptor

import com.sample.factpedia.core.common.result.BadRequestException
import com.sample.factpedia.core.common.result.ConflictException
import com.sample.factpedia.core.common.result.ForbiddenException
import com.sample.factpedia.core.common.result.InternalServerException
import com.sample.factpedia.core.common.result.NetworkException
import com.sample.factpedia.core.common.result.NotFoundException
import com.sample.factpedia.core.common.result.PreconditionFailedException
import com.sample.factpedia.core.common.result.TimeoutException
import com.sample.factpedia.core.common.result.UnauthorizedException
import com.sample.factpedia.core.common.result.UnavailableException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

@javax.inject.Singleton
class ResponseInterceptor @javax.inject.Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (_: SocketTimeoutException) {
            throw TimeoutException()
        } catch (_: IOException) {
            throw NetworkException()
        }

        if (!response.isSuccessful) {
            throw response.mapException()
        }

        return response
    }
}

private fun Response.mapException(): NetworkException = when (code) {
    HttpURLConnection.HTTP_BAD_REQUEST -> BadRequestException()
    HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedException()
    HttpURLConnection.HTTP_FORBIDDEN -> ForbiddenException()
    HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> TimeoutException()
    HttpURLConnection.HTTP_CONFLICT -> ConflictException()
    HttpURLConnection.HTTP_UNAVAILABLE -> UnavailableException()
    HttpURLConnection.HTTP_PRECON_FAILED -> PreconditionFailedException()
    in 500..599 -> InternalServerException()
    else -> NetworkException()
}