package com.sample.factpedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.datastore.ThemePreference
import com.sample.factpedia.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
): ViewModel() {

    private val _state = MutableStateFlow(ThemePreference.SYSTEM)
    val state = _state
        .onStart {
            observeTheme()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ThemePreference.SYSTEM
        )

    private fun observeTheme() {
        viewModelScope.launch {
            userPreferencesDataStore.themePreferenceFlow.collect { theme ->
                _state.value = theme
            }
        }
    }
}