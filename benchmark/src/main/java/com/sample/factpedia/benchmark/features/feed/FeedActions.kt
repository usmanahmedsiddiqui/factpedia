package com.sample.factpedia.benchmark.features.feed

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.sample.factpedia.benchmark.util.waitAndFindObject

fun MacrobenchmarkScope.feedContent() {
    val feedLoading = By.res("Feed loading")
    val didYouKnow = By.text("Did You Know?")
    device.wait(Until.gone(feedLoading), 5_000)
    device.waitAndFindObject(didYouKnow, 5_000)
}