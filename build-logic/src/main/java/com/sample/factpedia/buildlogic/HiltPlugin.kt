package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.google.dagger.hilt.android")
        pluginManager.apply("org.jetbrains.kotlin.kapt")

        val libs = getLibs()

        dependencies {
            add("implementation", libs.findLibrary("hilt.android").get())
            add("kapt", libs.findLibrary("hilt.compiler").get())
        }
    }
}