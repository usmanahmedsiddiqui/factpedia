plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.hilt)
    alias(libs.plugins.setup.serialization)
    alias(libs.plugins.setup.room)
}

android {
    namespace = "com.sample.factpedia.database"
}