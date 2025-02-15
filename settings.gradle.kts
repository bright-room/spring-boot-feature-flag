plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "spring-boot-feature-flag"

include("core")
include("web")
include("webflux")
