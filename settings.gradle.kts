rootProject.name = "micronaut-kotlin"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val micronautPluginVersion: String by settings

        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
        id("io.micronaut.application") version micronautPluginVersion
        id("com.github.johnrengelman.shadow") version "7.1.2"
    }
}
