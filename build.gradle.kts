 plugins {
        id("org.jetbrains.kotlin.jvm")
        id("org.jetbrains.kotlin.kapt")
        id("org.jetbrains.kotlin.plugin.allopen")
        id("com.github.johnrengelman.shadow") version "7.1.2"
        id("io.micronaut.application")
 }

version = "0.1"
group = "pl.mt"

repositories {
    mavenCentral()
}

val kotlinVersion = project.properties["kotlinVersion"]
val micronautSerdeVersion = project.properties["micronautSerdeVersion"]

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor:${micronautSerdeVersion}")
    implementation("io.micronaut.serde:micronaut-serde-jackson:${micronautSerdeVersion}")

    annotationProcessor("io.micronaut.data:micronaut-data-document-processor")

}


application {
    mainClass.set("example.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("19")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("pl.mt.*")
    }
}



