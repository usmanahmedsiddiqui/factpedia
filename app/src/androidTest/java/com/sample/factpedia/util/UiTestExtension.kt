package com.sample.factpedia.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.core.app.ApplicationProvider
import kotlin.properties.ReadOnlyProperty

fun AndroidComposeTestRule<*, *>.stringResource(
    @StringRes resId: Int,
): ReadOnlyProperty<Any, String> =
    ReadOnlyProperty { _, _ -> activity.getString(resId) }

fun String.loadJson(): String {
    val context = ApplicationProvider.getApplicationContext<Context>()
    return context.assets.open(this)
        .bufferedReader()
        .use { it.readText() }
}
