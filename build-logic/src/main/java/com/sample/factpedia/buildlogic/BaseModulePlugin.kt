package com.sample.factpedia.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BaseModulePlugin: Plugin<Project>  {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")

        val libs = getLibs()

        extensions.configure<LibraryExtension> {
            compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

            defaultConfig {
                minSdk = libs.findVersion("minSdk").get().toString().toInt()
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

            packaging {
                resources {
                    excludes += "/META-INF/LICENSE.md"
                    excludes += "/META-INF/LICENSE-notice.md"
                }
            }

            experimentalProperties["android.experimental.enableScreenshotTest"] = true
        }
    }

}