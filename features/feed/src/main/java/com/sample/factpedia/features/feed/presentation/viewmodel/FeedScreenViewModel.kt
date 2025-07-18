package com.sample.factpedia.features.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.domain.ObserveBookmarkStatusUseCase
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.features.feed.data.repository.FeedRepository
import com.sample.factpedia.features.feed.domain.usecase.GetRandomFactUseCase
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.state.FeedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val factsRepository: FactsRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val observeBookmarkStatusUseCase: ObserveBookmarkStatusUseCase,
    private val getRandomFactUseCase: GetRandomFactUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FeedScreenState())
    val state = _state
        .onStart {
            loadFact()
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FeedScreenState()
        )

    fun onAction(action: FeedScreenAction) {
        when (action) {
            is FeedScreenAction.ToggleBookmark -> toggleBookmark(action.factId, action.isBookmarked)
            FeedScreenAction.Refresh -> refreshFact()
            FeedScreenAction.Retry -> loadFact()
            is FeedScreenAction.ShareFact -> {}
        }
    }

    private fun refreshFact() {
        val count = state.value.refreshCount + 1
        _state.update { it.copy(refreshCount = count) }

        if (count % 3 == 0) prefetchFactsInBackground()
        loadFact()
    }

    private fun prefetchFactsInBackground() {
        viewModelScope.launch {
            feedRepository.loadRemoteFacts(LIMIT).fold(
                onSuccess = {
                    factsRepository.upsertFacts(it)
                },
                onFailure = {}
            )
        }
    }

    private fun loadFact() {
        viewModelScope.launch {
            val currentFact = state.value.fact
            val fact = getRandomFactUseCase(currentFact?.id)
            if (fact != null) {
                _state.update { it.copy(fact = fact, isLoading = false) }
                observeBookmarkStatus(fact.id)
            } else {
                fetchRemoteFacts()
            }
        }
    }

    private fun fetchRemoteFacts() {
        _state.update {
            it.copy(isLoading = true, error = null, fact = null)
        }

        viewModelScope.launch {
            feedRepository.loadRemoteFacts(LIMIT).fold(
                onSuccess = { facts ->
                    if (facts.isNotEmpty()) {
                        factsRepository.upsertFacts(facts)
                        loadFact()
                    } else {
                        _state.update { it.copy(isLoading = false) }
                    }

                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error, fact = null) }
                }
            )
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }

    private fun observeBookmarkStatus(factId: Int) {
        viewModelScope.launch {
            observeBookmarkStatusUseCase(factId).collectLatest { isBookmarked ->
                _state.update {
                    it.copy(fact = it.fact?.copy(isBookmarked = isBookmarked))
                }
            }
        }
    }
}

private const val LIMIT = 10

