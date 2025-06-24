plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.retrofit)
    alias(libs.plugins.setup.serialization)
}

android {
    namespace = "com.sample.factpedia.core.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
}
