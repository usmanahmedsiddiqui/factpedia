package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DataStorePlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()
        dependencies {
            add("implementation", libs.findLibrary("datastore.preferences.core").get())
            add("implementation", libs.findLibrary("datastore.preferences").get())
        }
    }
}