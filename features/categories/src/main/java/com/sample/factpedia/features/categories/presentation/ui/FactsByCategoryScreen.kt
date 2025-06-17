package com.sample.factpedia.features.categories.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.viewmodel.FactsByCategoryViewModel

@Composable
fun FactsByCategoryScreen(
    categoryId: Int,
    categoryName: String,
    viewModel: FactsByCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    when {
        state.isLoading -> {}
        state.error != null -> {}
        state.facts.isNotEmpty() -> {
            Column {
                Text(
                    text = categoryName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                LazyColumn {
                    items(state.facts) { item ->
                        Text(
                            text = item.fact,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    viewModel.onAction(FactsByCategoryScreenAction.LoadFactsByCategory(categoryId))
}