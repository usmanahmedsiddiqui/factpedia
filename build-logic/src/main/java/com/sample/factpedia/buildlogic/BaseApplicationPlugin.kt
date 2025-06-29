package com.sample.factpedia.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BaseApplicationPlugin: Plugin<Project>  {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")

        val libs = getLibs()

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

            defaultConfig {
                applicationId = "com.sample.factpedia"
                minSdk = libs.findVersion("minSdk").get().toString().toInt()
                targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }

            packaging {
                resources {
                    excludes += "/META-INF/LICENSE.md"
                    excludes += "/META-INF/LICENSE-notice.md"
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    configureKotlinJvmOptions()
                }
            }

            buildFeatures.compose = true
        }
    }

}