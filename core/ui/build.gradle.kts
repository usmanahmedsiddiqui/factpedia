plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
}

android {
    namespace = "com.sample.factpedia.core.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
}
