plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.compose)
    alias(libs.plugins.setup.navigation)
    alias(libs.plugins.compose.screenshot)
    alias(libs.plugins.setup.screenshot.testing)

}

android {
    namespace = "com.sample.factpedia.designsystem"
}