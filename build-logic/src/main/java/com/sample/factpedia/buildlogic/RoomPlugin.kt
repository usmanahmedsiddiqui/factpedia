package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.kapt")

        val libs = getLibs()

        dependencies {
            add("implementation", libs.findLibrary("room.runtime").get())
            add("implementation", libs.findLibrary("room.ktx").get())
            add("kapt", libs.findLibrary("room.compiler").get())
        }
    }
}