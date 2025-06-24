package com.sample.factpedia.core.common.result

sealed interface Response<out D, out E : Error> {
    data class Success<out D, out E : Error>(val data: D) : Response<D, E>
    data class Failure<out D, out E : Error>(val  error: E) : Response<D, E>
}

inline fun <D, E : Error, R> Response<D, E>.map(transform: (D) -> R): Response<R, E> = when (this) {
    is Response.Success -> Response.Success(transform(this.data))
    is Response.Failure -> Response.Failure(this.error)
}

inline fun <D, E : Error, F : Error> Response<D, E>.mapError(transform: (E) -> F): Response<D, F> = when (this) {
    is Response.Success -> Response.Success(this.data)
    is Response.Failure -> Response.Failure(transform(this.error))
}

inline fun <D, E : DataError> Response<D, E>.fold(
    onSuccess: (D) -> Unit,
    onFailure: (E) -> Unit
) {
    when (this) {
        is Response.Success -> onSuccess(data)
        is Response.Failure -> onFailure(error)
    }
}