import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
    id("maven-publish")
    id("org.jetbrains.dokka").version("0.9.18")
    java
    jacoco
}

val kotlin_version = "1.3.72"
val csv_version = "0.10.4"

repositories {
    jcenter()
}

val dokkaJar = task<Jar>("dokkaJar") {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                noReflect = false
            }
        }
        mavenPublication {
            artifact(dokkaJar)
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
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
                implementation("io.github.microutils:kotlin-logging:1.7.9")
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

val jvmTest by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.jacocoTestReport {
    val coverageSourceDirs = arrayOf(
            "commonMain/src",
            "jvmMain/src"
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
