plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.hilt)
}

android {
    namespace = "com.sample.factpedia.core.domain"

}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
}