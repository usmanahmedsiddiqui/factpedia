plugins {
    alias(libs.plugins.setup.base.module)
    alias(libs.plugins.setup.coroutine)
    alias(libs.plugins.setup.hilt)
}

android {
    namespace = "com.sample.factpedia.core.common"
}
