package com.sample.factpedia.benchmark.features.categories

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.sample.factpedia.benchmark.util.waitAndFindObject

fun MacrobenchmarkScope.factByCategoriesContent() {
    val factsByCategoryLoading = By.descContains("Loading facts by category")
    val factsByCategoryList = By.descContains("factsByCategory:list")
    device.wait(Until.gone(factsByCategoryLoading), 5_000)
    device.waitAndFindObject(factsByCategoryList, 5_000)
}