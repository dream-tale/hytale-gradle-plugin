plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "hytale-gradle-plugin"
include("plugin")
project(":plugin").name = "hytale-gradle-plugin"
