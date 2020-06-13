plugins {
    kotlin("multiplatform") version "1.3.72"
}

val kotlin_version = "1.3.72"
val csv_version = "0.10.4"

group = "org.blackmo"
version = "0.0.1"

repositories {
    mavenCentral()
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
