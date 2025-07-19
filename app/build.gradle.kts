plugins {
    alias(libs.plugins.setup.application.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.setup.retrofit)
    alias(libs.plugins.setup.junit.testing)
    alias(libs.plugins.setup.ui.testing)
}

android {
    namespace = "com.sample.factpedia"

    defaultConfig {
        testInstrumentationRunner = "com.sample.factpedia.core.testing.FactPediaTestRunner"
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.testing)

    implementation(projects.features.bookmarks)
    implementation(projects.features.categories)
    implementation(projects.features.feed)
    implementation(projects.features.search)
    implementation(projects.features.settings)

    androidTestImplementation(libs.hilt.android.testing)
}