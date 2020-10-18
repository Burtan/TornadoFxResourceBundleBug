import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    repositories {
        jcenter()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

plugins {
    application
    kotlin("jvm") version "1.4.10"
}

application {
    mainModule.set("com.example")
    mainClass.set("com.example.MyApp")
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

java {
    modularity.inferModulePath.set(true)
}

// fixes empty packages warning in module-info.java
tasks {
    compileJava {
        inputs.property("moduleName", "com.example")
        options.javaModuleMainClass.set("com.example.MyApp")
        options.compilerArgs = listOf(
            "--patch-module", "com.example=${sourceSets.main.get().output.asPath}"
        )
    }
}

dependencies {
    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")

    //TornadoFX
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT") {
        exclude("org.jetbrains.kotlin")
        exclude("org.openjfx")
    }

    val jfxOptions = object {
        val group = "org.openjfx"
        val version = "15"
        val fxModules = arrayListOf(
            "javafx-base",
            "javafx-controls",
            "javafx-graphics",
            "javafx-media",
            "javafx-swing",
            "javafx-web",
            "javafx-fxml"
        )
    }
    jfxOptions.run {
        val osName = System.getProperty("os.name")
        val platform = when {
            osName.startsWith("Mac", ignoreCase = true) -> "mac"
            osName.startsWith("Windows", ignoreCase = true) -> "win"
            osName.startsWith("Linux", ignoreCase = true) -> "linux"
            else -> "mac"
        }
        fxModules.forEach {
            implementation("$group:$it:$version:$platform")
        }
    }
}
