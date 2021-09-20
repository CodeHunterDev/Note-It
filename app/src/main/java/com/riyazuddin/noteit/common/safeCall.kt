package com.riyazuddin.noteit.common

import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED

inline fun <T> safeCall(
    action: () -> Resource<T>
): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: AN_UNKNOWN_ERROR_OCCURRED)
    }
}