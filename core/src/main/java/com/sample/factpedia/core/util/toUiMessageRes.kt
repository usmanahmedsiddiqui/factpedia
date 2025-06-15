package com.sample.factpedia.core.util

import android.content.Context
import com.sample.factpedia.core.R
import com.sample.factpedia.core.common.result.DataError

fun DataError.toUiMessageRes(context: Context): String {
    return when (this) {
        is DataError.Network -> when (this) {
            DataError.Network.BAD_REQUEST -> context.getString(R.string.error_bad_request)
            DataError.Network.CONFLICT -> context.getString(R.string.error_conflict)
            DataError.Network.FORBIDDEN -> context.getString(R.string.error_forbidden)
            DataError.Network.INTERNAL_SERVER -> context.getString(R.string.error_internal_server)
            DataError.Network.NOT_FOUND -> context.getString(R.string.error_not_found)
            DataError.Network.TIMEOUT -> context.getString(R.string.error_timeout)
            DataError.Network.UNAUTHORIZED -> context.getString(R.string.error_unauthorized)
            DataError.Network.UNAVAILABLE -> context.getString(R.string.error_unavailable)
            DataError.Network.PRECONDITION_FAILED -> context.getString(R.string.error_precondition_failed)
        }
    }
}