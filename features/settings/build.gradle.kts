plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.navigation)
}

android {
    namespace = "com.sample.factpedia.features.settings"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.datastore)
}