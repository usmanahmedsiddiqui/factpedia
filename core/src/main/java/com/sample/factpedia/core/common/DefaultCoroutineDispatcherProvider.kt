package com.sample.factpedia.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultCoroutineDispatcherProvider @Inject constructor(): CoroutineDispatcherProvider {
    override val mainDispatcher: CoroutineDispatcher get() = Dispatchers.Main
    override val ioDispatcher: CoroutineDispatcher get() = Dispatchers.IO
    override val defaultDispatcher: CoroutineDispatcher get() = Dispatchers.Default
}