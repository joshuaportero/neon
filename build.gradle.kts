import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.portero"
version = "1.0.0-ALPHA"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    implementation("org.reflections:reflections:0.10.2")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("project" to project)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
}