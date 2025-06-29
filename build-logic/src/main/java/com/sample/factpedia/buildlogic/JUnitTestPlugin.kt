package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class JUnitTestPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = getLibs()

        dependencies {
            add("testImplementation", libs.findLibrary("junit5.api").get())
            add("testImplementation", libs.findLibrary("junit5.engine").get())
            add("testImplementation", libs.findLibrary("kotlin.test").get())
            add("testImplementation", libs.findLibrary("truth").get())
            add("testImplementation", libs.findLibrary("turbine").get())
            add("testImplementation", libs.findLibrary("coroutines.test").get())
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}