package com.sample.factpedia.core.common.result

sealed interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        TIMEOUT,
        CONFLICT,
        UNAVAILABLE,
        PRECONDITION_FAILED,
        INTERNAL_SERVER,
    }
}