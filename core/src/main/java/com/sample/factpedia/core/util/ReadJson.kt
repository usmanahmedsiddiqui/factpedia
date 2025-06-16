package com.sample.factpedia.core.util

import android.content.Context

fun Context.readAssetFile(fileName: String): String {
    return this.assets.open(fileName).bufferedReader().use { it.readText() }
}