package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NavigationPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()

        dependencies {
            add("implementation", libs.findLibrary("navigation.compose").get())
            add("implementation", libs.findLibrary("hilt.navigation.compose").get())
        }
    }
}