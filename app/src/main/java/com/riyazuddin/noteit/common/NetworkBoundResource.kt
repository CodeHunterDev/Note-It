package com.riyazuddin.noteit.common

import kotlinx.coroutines.flow.*
import timber.log.Timber

/**
 * ResultType -> Note from database
 * RequestType -> whatever api send
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(data)
        try {
            val fetchedResult = fetch()
            saveFetchResult(fetchedResult)
            query()
        }catch (e: Exception){
            Timber.e(e)
            onFetchFailed(e)
            query()
        }
    } else {
        query()
    }

    emitAll(flow)
}