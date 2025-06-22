package com.sample.factpedia.features.feed.domain.usecase

import javax.inject.Inject

class ShouldPrefetchUseCase @Inject constructor() {
    operator fun invoke(refreshCount: Int): Boolean = refreshCount % 3 == 0
}