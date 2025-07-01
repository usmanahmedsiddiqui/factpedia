plugins {
    alias(libs.plugins.setup.application.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.setup.junit.testing)
}

android {
    namespace = "com.sample.factpedia"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.testing)

    implementation(projects.features.bookmarks)
    implementation(projects.features.categories)
    implementation(projects.features.feed)
    implementation(projects.features.search)
    implementation(projects.features.settings)
}