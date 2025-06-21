plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sample.factpedia"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sample.factpedia"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.icons.extended)
    debugImplementation(libs.compose.tooling)

    // ViewModel + Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.viewmodel.compose)

    // Navigation
    implementation(libs.navigation.compose)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.android)

    // Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.features.categories)
    implementation(projects.features.search)
    implementation(projects.features.bookmarks)
    implementation(projects.features.feed)
    implementation(projects.core)
}