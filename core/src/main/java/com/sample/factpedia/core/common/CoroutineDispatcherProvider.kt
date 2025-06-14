package com.sample.factpedia.core.common

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
}