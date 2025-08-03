package com.sample.factpedia.benchmark.features.categories

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.sample.factpedia.benchmark.util.flingElementDownUp
import com.sample.factpedia.benchmark.util.waitAndFindObject
import org.junit.Assert.fail

fun MacrobenchmarkScope.goToCategories() {
    device.findObject(By.text("Categories")).click()
    device.waitForIdle()
}

fun MacrobenchmarkScope.categoriesContent() {
    val categoriesLoading = By.descContains("Loading categories")
    val categoriesList = By.descContains("categories:list")
    device.wait(Until.gone(categoriesLoading), 5_000)
    device.waitAndFindObject(categoriesList, 5_000)
}

fun MacrobenchmarkScope.categoriesScrollTopicsDownUp() {
    val categoriesList = By.descContains("categories:list")
    val obj = device.waitAndFindObject(categoriesList, 5_000)
    device.flingElementDownUp(obj)
}

fun MacrobenchmarkScope.selectCategory() {
    val categoriesList = By.descContains("categories:list")
    val obj = device.waitAndFindObject(categoriesList, 5_000)
    if (obj.childCount == 0) {
        fail("No topics found, can't generate profile for ForYou page.")
    }

    obj.children[0].click()
}