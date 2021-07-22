val kotlinVersion = "1.5.10"
val ktorVersion = "1.5.4"
val csvVersion = "0.15.2"
val coroutineVersion = "1.5.0"

group = "io.github.blackmo18"
version = "0.7.1"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}
