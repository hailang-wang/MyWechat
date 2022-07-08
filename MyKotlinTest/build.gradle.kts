import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath(kotlin("gradle-plugin", version = "1.6.20"))
    }
}

allprojects {
    tasks {
        withType<JavaCompile>() {
            with(options) {
                compilerArgs = compilerArgs + listOf("-Xlint:unchecked", "-Xlint:deprecation")
                sourceCompatibility = "1.8"
                targetCompatibility = "1.8"
            }
        }
        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
                jvmTarget = "1.8"
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}