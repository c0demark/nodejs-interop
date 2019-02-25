import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    application
}

group = "net.plan99.nodejs"
version = "1.0"

application {
    mainClassName = "net.plan99.nodejs.sample.DatExplorer"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(rootProject)   // This would be:   implementation("net.plan99.nodejs:nodejs-interop:1.0") in a real project
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task<Exec>("startjs") {
    dependsOn("build")
    setCommandLine(
        "/Users/mike/graalvm-ce-1.0.0-rc12/Contents/Home/bin/node",
        "--jvm",
        "--jvm.cp", sourceSets["main"].runtimeClasspath.asPath,
        "--experimental-worker",
        "../src/main/resources/boot.js",
        application.mainClassName,
        "one",
        "two",
        "three"
    )
}
