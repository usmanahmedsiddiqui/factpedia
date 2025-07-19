plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.setup.ui.testing)
    alias(libs.plugins.setup.screenshot.testing)
    alias(libs.plugins.compose.screenshot)

}

android {
    namespace = "com.sample.factpedia.features.settings"

    defaultConfig {
        testInstrumentationRunner = "com.sample.factpedia.core.testing.FactPediaTestRunner"
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.testing)
}