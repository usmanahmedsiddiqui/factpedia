package com.sample.factpedia.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

internal fun dispatchMockResponses(request: RecordedRequest): MockResponse {
    val path = request.path.orEmpty()
    return when {
        path == CATEGORY_PATH -> generateSuccessMockResponse("categories.json")
        path.matches(FACT_BY_CATEGORIES_PATH) -> generateSuccessMockResponse("facts_by_category.json")
        path.matches(FEED_PATH) -> generateSuccessMockResponse("feed.json")
        else -> notFoundResponse
    }
}

private fun generateSuccessMockResponse(filePath: String): MockResponse {
    val jsonString = filePath.loadJson()
    return MockResponse()
        .setResponseCode(200)
        .setBody(jsonString)
}

private val notFoundResponse = MockResponse()
    .setResponseCode(200)


private const val CATEGORY_PATH = "/categories"
private val FACT_BY_CATEGORIES_PATH = "/categories/\\d+/facts".toRegex()
private val FEED_PATH = "/facts/\\d+".toRegex()