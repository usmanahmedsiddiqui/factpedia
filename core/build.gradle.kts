plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.retrofit)
}

android {
    namespace = "com.sample.factpedia.core"
}

dependencies {
    implementation(projects.database)
    implementation(projects.designsystem)
}