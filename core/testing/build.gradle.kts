plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.coroutine)
}

android {
    namespace = "com.sample.factpedia.core.testing"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.database)

    implementation(libs.coroutines.test)
    implementation(libs.junit5.api)
}