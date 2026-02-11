plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`

}

version = "1.0.0"
group = "net.dreamtale"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

gradlePlugin {
    plugins {
        register("hytale-gradle-plugin") {
            id = "net.dreamtale.hytale-gradle-plugin"
            implementationClass = "net.dreamtale.gradle.plugin.hytale.HytaleGradlePlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/dream-tale/hytale-gradle-plugin")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
