package com.sample.factpedia.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RetrofitPlugin : Plugin<Project> {
    override fun apply(project: Project)  = with(project) {
        val libs = getLibs()
        dependencies {
            add("implementation", libs.findLibrary("retrofit2").get())
            add("implementation", libs.findLibrary("retrofit2.gsonconverter").get())
            add("implementation", libs.findLibrary("gson").get())
            add("implementation", libs.findLibrary("okhttp3").get())
            add("implementation", libs.findLibrary("okhttp3.logginginterceptor").get())
        }
    }
}