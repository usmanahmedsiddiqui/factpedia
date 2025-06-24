plugins {
    `kotlin-dsl`
}

dependencies {
     implementation(libs.gradle)
     implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("setup-application-module") {
            id = "com.sample.factpedia.plugin.setup-application-module"
            implementationClass = "com.sample.factpedia.buildlogic.BaseApplicationPlugin"
        }

        register("setup-base-module") {
            id = "com.sample.factpedia.plugin.setup-base-module"
            implementationClass = "com.sample.factpedia.buildlogic.BaseModulePlugin"
        }

        register("setup-compose") {
            id = "com.sample.factpedia.plugin.setup-compose"
            implementationClass = "com.sample.factpedia.buildlogic.ComposePlugin"
        }

        register("setup-datastore") {
            id = "com.sample.factpedia.plugin.setup-datastore"
            implementationClass = "com.sample.factpedia.buildlogic.DataStorePlugin"
        }

        register("setup-coroutine") {
            id = "com.sample.factpedia.plugin.setup-coroutine"
            implementationClass = "com.sample.factpedia.buildlogic.CoroutinePlugin"
        }

        register("setup-hilt") {
            id = "com.sample.factpedia.plugin.setup-hilt"
            implementationClass = "com.sample.factpedia.buildlogic.HiltPlugin"
        }

        register("setup-navigation") {
            id = "com.sample.factpedia.plugin.setup-navigation"
            implementationClass = "com.sample.factpedia.buildlogic.NavigationPlugin"
        }

        register("setup-retrofit") {
            id = "com.sample.factpedia.plugin.setup-retrofit"
            implementationClass = "com.sample.factpedia.buildlogic.RetrofitPlugin"
        }

        register("setup-room") {
            id = "com.sample.factpedia.plugin.setup-room"
            implementationClass = "com.sample.factpedia.buildlogic.RoomPlugin"
        }

        register("setup-serialization") {
            id = "com.sample.factpedia.plugin.setup-serialization"
            implementationClass = "com.sample.factpedia.buildlogic.SerializationPlugin"
        }
    }
}