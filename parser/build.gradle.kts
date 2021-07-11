plugins {
    java
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
    jacoco
}

val kotlinVersion = "1.4.32"
val csvVersion = "0.15.2"
val coroutineVersion = "1.4.3"

group = "io.github.blackmo18"
version = "0.7.1"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.9.18")
    }
}

repositories {
    mavenCentral()
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
        mavenPublication {
            artifact(dokkaJar)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
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
                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:$csvVersion")
            }
        }
    }
}



publishing {
    publications.all {
        (this as MavenPublication).pom {
            name.set("kotlin-grass")
            description.set("Csv File to Kotlin Data Class Parser")
            url.set("https://github.com/blackmo18/kotlin-grass")

            organization {
                name.set("io.github.blackmo18")
                url.set("https://github.com/blackmo18")
            }
            licenses {
                license {
                    name.set("Apache License 2.0")
                    url.set("https://github.com/blackmo18/kotlin-grass/blob/master/LICENSE")
                }
            }
            scm {
                url.set("https://github.com/blackmo18/kotlin-grass")
                connection.set("scm:git:git://github.com/blackmo18/kotlin-grass.git")
                developerConnection.set("https://github.com/blackmo18/kotlin-grass")
            }
            developers {
                developer {
                    name.set("blackmo18")
                }
            }
        }
    }
    repositories {
        maven {
            credentials {
                val nexusUsername: String? by project
                val nexusPassword: String? by project
                username = nexusUsername
                password = nexusPassword
            }

            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    sign(publishing.publications)
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
        .filter { it.isFile }
        .filterNot {
            val fileNamePath = it.absolutePath
            val dir = fileNamePath.substring(0, fileNamePath.lastIndexOf(File.separator))
            dir.contains("io/github/blackmo18/grass/data")
        }

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
