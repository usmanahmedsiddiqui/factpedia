package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CoroutinePlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()

        dependencies {
            add("implementation", libs.findLibrary("coroutines.android").get())
        }
    }
}