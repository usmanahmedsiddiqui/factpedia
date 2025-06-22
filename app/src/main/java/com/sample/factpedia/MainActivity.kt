package com.sample.factpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.ui.FactPediaApp
import com.sample.factpedia.ui.rememberFactPediaAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberFactPediaAppState()
            FactPediaTheme {
                FactPediaApp(appState)
            }
        }
    }
}