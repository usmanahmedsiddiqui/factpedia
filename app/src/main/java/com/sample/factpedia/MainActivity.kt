package com.sample.factpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.ui.FactPediaApp
import com.sample.factpedia.ui.rememberFactPediaAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val appState = rememberFactPediaAppState()
            val themePreference = viewModel.state.collectAsStateWithLifecycle().value
            val isDarkTheme = when (themePreference) {
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
            }

            FactPediaTheme(darkTheme = isDarkTheme) {
                FactPediaApp(appState, isDarkTheme)
            }
        }
    }
}