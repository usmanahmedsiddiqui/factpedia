package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SerializationPlugin: Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        val libs = getLibs()

        dependencies {
            add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
        }
    }
}