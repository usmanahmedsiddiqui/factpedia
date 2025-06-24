package com.sample.factpedia.buildlogic

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun KotlinJvmOptions.configureKotlinJvmOptions() = apply {
    freeCompilerArgs = freeCompilerArgs +
        listOf(
            "-opt-in=kotlin.contracts.ExperimentalContracts",
            "-Xjvm-default=all-compatibility",
        )
    jvmTarget = Tools.javaVersion.toString()
}