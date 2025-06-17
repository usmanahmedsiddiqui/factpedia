package com.sample.factpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sample.factpedia.features.categories.presentation.navigation.CategoryListScreenRoute
import com.sample.factpedia.features.categories.presentation.navigation.FactsByCategoryScreenRoute
import com.sample.factpedia.features.categories.presentation.ui.CategoryListScreen
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryScreen
import com.sample.factpedia.features.search.presentation.navigation.SearchScreenRoute
import com.sample.factpedia.features.search.presentation.ui.SearchScreen
import com.sample.factpedia.ui.theme.FactPediaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FactPediaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CategoryListScreenRoute
                ) {
                    composable<CategoryListScreenRoute> {
                        CategoryListScreen { category ->
                            navController.navigate(
                                FactsByCategoryScreenRoute(
                                    categoryId = category.id,
                                    categoryNane = category.name
                                )
                            )
                        }
                    }

                    composable<FactsByCategoryScreenRoute> {
                        val args = it.toRoute<FactsByCategoryScreenRoute>()
                        FactsByCategoryScreen(args.categoryId, args.categoryNane)
                    }

                    composable<SearchScreenRoute> {
                        SearchScreen()
                    }
                }
            }
        }
    }
}