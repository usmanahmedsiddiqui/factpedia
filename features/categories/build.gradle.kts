plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.retrofit)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.setup.junit.testing)
    alias(libs.plugins.setup.ui.testing)
    alias(libs.plugins.compose.screenshot)
    alias(libs.plugins.setup.screenshot.testing)
}

android {
    namespace = "com.sample.factpedia.features.categories"

    defaultConfig {
        testInstrumentationRunner = "com.sample.factpedia.core.testing.FactPediaTestRunner"
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.testing)
    implementation(projects.core.ui)
}