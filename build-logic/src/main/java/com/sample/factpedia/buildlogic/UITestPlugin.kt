package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class UITestPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()

        dependencies {
            add("androidTestImplementation", libs.findLibrary("compose.ui.test").get())
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4.android").get())
            add("androidTestImplementation", libs.findLibrary("espresso.core").get())
            add("androidTestImplementation", libs.findLibrary("okhttp3.mockwebserver").get())
            add("androidTestImplementation", libs.findLibrary("jsonpath").get())
        }
    }
}