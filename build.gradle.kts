plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.nsane.diesel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "hytale"
        url = uri("https://maven.hytale.com/release")
    }
}

dependencies {
    compileOnly("com.hypixel.hytale:Server:+")

    implementation("io.github.hytalekt:kytale:+")
    implementation("io.github.hytalekt:kytale-serialization:+")
    implementation("io.github.hytalekt:kytale-coroutines:+")
    implementation(files("libs/Vuetale-1.0.16-all.jar"))
}

kotlin {
    jvmToolchain(25)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

//move the jar to your hytale mods directory
//okay it turns out there's a dev server that's not mentioned in the docs but i'm leaving this here out of spite
//i guess it makes it simpler to test the end user experience?
tasks.named("build") {
    doLast {
        val path = findProperty("hytale.home_path")
        println("HYTALE PATH IS ${path} and PROJECT NAME IS ${project.name}")
        val libs = layout.buildDirectory.dir("libs").get().asFile
        val jar = file("build/libs/${project.name}.jar")
        val finalpath = "$path/UserData/Mods/${jar.name}"
        //ok now move it
        jar?.copyTo(file(finalpath),overwrite = true)
        println("MOVED DEV JAR TO ${finalpath}")
    }
}

tasks.shadowJar {
    // Relocate Kytale
    relocate("io.github.hytalekt.kytale", "com.nsane.diesel.kytale")

    // Include dependencies
    configurations.set(listOf(project.configurations.runtimeClasspath.get()))

    archiveClassifier.set("")
}

tasks.build {
    dependsOn("shadowJar")
}