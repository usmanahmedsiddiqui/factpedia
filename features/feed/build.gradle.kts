plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.retrofit)
    alias(libs.plugins.setup.navigation)
}

android {
    namespace = "com.sample.factpedia.features.feed"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.ui)
}