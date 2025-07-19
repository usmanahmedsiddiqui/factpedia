package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ScreenShotTestPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()

        dependencies {
            add("screenshotTestImplementation", libs.findLibrary("compose.tooling").get())
            add("screenshotTestImplementation", libs.findLibrary("screenshot.validation.api").get())
        }
    }
}