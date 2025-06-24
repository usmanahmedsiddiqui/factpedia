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
    implementation(projects.designsystem)
    implementation(projects.core)
    implementation(projects.datastore)
}