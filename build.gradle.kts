plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application")
    id("io.micronaut.test-resources")
}

version = "0.1"
group = "pl.mt"

repositories {
    mavenCentral()
}

val kotlinVersion = project.properties["kotlinVersion"]
val micronautSerdeVersion = project.properties["micronautSerdeVersion"]
val testContainersVersion = project.properties["testContainersVersion"]

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    implementation("io.micronaut.serde:micronaut-serde-jackson:${micronautSerdeVersion}")
    implementation("jakarta.annotation:jakarta.annotation-api")

    annotationProcessor("io.micronaut.serde:micronaut-serde-processor:${micronautSerdeVersion}")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.mongodb:mongodb-driver-sync")

    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.testcontainers:testcontainers:${testContainersVersion}")
    testImplementation("org.testcontainers:mongodb:${testContainersVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
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



