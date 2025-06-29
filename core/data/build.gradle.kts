plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.hilt)
}

android {
    namespace = "com.sample.factpedia.core.data"
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.model)
}