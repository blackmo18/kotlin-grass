import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
    id("org.jetbrains.dokka").version("0.9.18")
    id("com.jfrog.bintray") version "1.8.5"
    id("java-library")
    jacoco
}

val kotlin_version = "1.3.72"
val csv_version = "0.10.4"

group = "com.vhl.blackmo"
version = "0.2.0"

repositories {
    jcenter()
}

val dokkaJar = task<Jar>("dokkaJar") {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
}
val sourceJar = task<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}
val metadata = task<Jar>("metadata") {
    archiveClassifier.set("metadata")
    from(sourceSets.getByName("main").allSource)
}

val jvm = task<Jar>("jvm") {
    archiveClassifier.set("jvm")
    from(sourceSets.getByName("main").allSource)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                noReflect = false
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-annotations-common"))
            }
        }
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        jvm().compilations["test"].defaultSourceSet {
            this.
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:$csv_version")
            }
        }
    }
}

val bintrayUser = (project.findProperty("bintray.user") ?: "").toString()
val bintrayKey = (project.findProperty("bintray.apikey")?: "").toString()

publishing {
    repositories {
        maven(url = "https://api.bintray.com/maven/blackmo18/kotlin-libraries/kotlin-grass/;publish=1") {
            name = "bintray"
            credentials {
                username = bintrayUser
                password = bintrayKey
            }
        }
    }
}

val jvmTest by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.jacocoTestReport {
    val coverageSourceDirs = arrayOf(
            "src/commonMain",
            "src/jvmMain"
    )
    val classFiles = File("${buildDir}/classes/kotlin/jvm/")
            .walkBottomUp()
            .toSet()
    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))
    additionalSourceDirs.setFrom(files(coverageSourceDirs))

    executionData
            .setFrom(files("${buildDir}/jacoco/jvmTest.exec"))

    reports {
        xml.isEnabled = true
        html.isEnabled = false
    }
}
