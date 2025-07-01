plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.datastore)
}

android {
    namespace = "com.sample.factpedia.datastore"
}

dependencies {
    implementation(projects.core.model)
}