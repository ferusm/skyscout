import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "org.example"
version = "0.1.0-SNAPSHOT"

tasks.withType<Jar> { manifest { attributes(mapOf("Main-Class" to "ApplicationKt")) } }
tasks.withType<KotlinCompile> { kotlinOptions { jvmTarget = "1.8" } }
tasks.withType<Test> { useJUnitPlatform() }

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("commons-validator:commons-validator:1.7")
    testImplementation("io.kotest:kotest-runner-junit5:4.3.1")
    testImplementation("io.kotest:kotest-assertions-core:4.3.1")
    testImplementation("io.kotest:kotest-property:4.3.1")
}
