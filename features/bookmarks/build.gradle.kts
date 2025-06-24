plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.hilt)
}

android {
    namespace = "com.sample.factpedia.features.bookmarks"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)
}