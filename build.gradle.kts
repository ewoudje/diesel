/**
 * NOTE: This is entirely optional and basics can be done in `settings.gradle.kts`
 */

repositories {
    // Any external repositories besides: MavenLocal, MavenCentral, HytaleMaven, and CurseMaven
}

dependencies {
    // Any external dependency you also want to include
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