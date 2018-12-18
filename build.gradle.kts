plugins {
    java
    application
}

group = "org.donut-lang"
version = "NaN"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    compile("org.slf4j:slf4j-simple:1.7.25")
    compile("info.picocli:picocli:3.8.2")
    compile("org.jetbrains:annotations:16.0.3")
    compile("org.json:json:20180813")
    compile("commons-codec:commons-codec:1.11")
}

val entryPoint = "donut.Main"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_10
}

application {
    mainClassName = entryPoint
}

val gitVer = task("gitVer", type = Exec::class) {
    commandLine("bash", "generate.sh")
}

val fullJar = task("fullJar", type = Jar::class) {
    baseName = "${project.name}-full"

    manifest {
        attributes["Main-Class"] = entryPoint
    }
    from(configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build"(Task::class) {
        dependsOn(gitVer)
        dependsOn(fullJar)
    }

    task("run-donut", type = JavaExec::class) {
        main = "donut.Main"
        classpath = sourceSets["main"].runtimeClasspath
        args()
        dependsOn(gitVer)
    }
}

