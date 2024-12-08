/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.9/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.jvm)
    
    // Aplly Dokka plugin
    id("org.jetbrains.dokka") version "1.9.20"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    
    // maven publish
	`maven-publish`
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

group = "com.andihasan7.lib-vsop87d-elpmpp02"
version = "8.0-SNAPSHOT"

publishing {
	publications {
		create<MavenPublication>("Maven") {
			from(components["java"])
		}
	}
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(libs.deephaven)
    runtimeOnly(libs.deephaven.double.parser)
    // implementation(libs.coroutines)
    // implementation(libs.kryo5)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.dokkaHtml.configure {
    // custom dokka output directory
    outputDirectory.set(file("../docs"))
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    // Memisahkan member agar tampil menjadi tab
    pluginsMapConfiguration.set(
        mapOf("org.jetbrains.dokka.base.DokkaBase" to """{ "separateInheritedMembers": true }""")
    )
}

//tasks.withType<Jar> {
//    from(sourceSets.main.get().resources) {
//        into("resources") // Menambahkan resource ke dalam JAR di folder resources
//    }
//}
