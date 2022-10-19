pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PhotoExplorer"

include(":app")
include(":data:data-remote")
include(":data")
include(":domain")
include(":app:feature-photos")
include(":app:feature-core")
