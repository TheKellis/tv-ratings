import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:8.2.0")
    }
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")

plugins {
    kotlin("jvm") version "1.3.41"
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.41"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.41"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.kevinellis"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.springfox:springfox-swagger2:2.7.0")
    implementation("io.springfox:springfox-swagger-ui:2.7.0")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-jdbc")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.+")
    compile("org.xerial:sqlite-jdbc:3.25.2")
    compile("org.projectlombok:lombok")

    // test
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
}