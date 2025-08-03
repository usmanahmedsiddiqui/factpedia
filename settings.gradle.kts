enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FactPedia"
include(":app")
include(":features:bookmarks")
include(":features:search")
include(":features:settings")
include(":features:feed")
include(":features:categories")
include(":core:designsystem")
include(":core:database")
include(":core:datastore")
include(":core:common")
include(":core:ui")
include(":core:network")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:testing")
include(":benchmark")
