package com.sample.factpedia.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

fun Project.findVersion(dependency: String): String = getLibs().findVersion(dependency).get().toString()

fun Project.getLibs(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")