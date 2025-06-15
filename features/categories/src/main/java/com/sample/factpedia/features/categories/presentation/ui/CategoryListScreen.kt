package com.sample.factpedia.features.categories.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.presentation.viewmodel.CategoriesViewModel

@Composable
fun CategoryListScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (Category) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    when {
        state.isLoading -> {}
        state.error != null -> {}
        state.categories.isNotEmpty() -> {
            LazyColumn {
                items(state.categories) { category ->
                    Text(
                        text = category.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCategoryClick(category) }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}