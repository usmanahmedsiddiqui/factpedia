package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposePlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        val libs = getLibs()
        dependencies {
            add("implementation", libs.findLibrary("compose.ui").get())
            add("implementation", libs.findLibrary("compose.preview").get())
            add("implementation", libs.findLibrary("compose.material3").get())
            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            add("implementation", libs.findLibrary("compose.icons.extended").get())
            add("debugImplementation", libs.findLibrary("compose-tooling").get())
        }
    }
}