import net.minecraftforge.gradle.common.util.MinecraftExtension
import java.text.SimpleDateFormat
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    }
}

apply(plugin = "kotlin")
apply(from = "https://raw.githubusercontent.com/thedarkcolour/KotlinForForge/site/thedarkcolour/kotlinforforge/gradle/kff-3.7.1.gradle")

plugins {
    eclipse
    `maven-publish`

    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
}

version = "1.19.2-0.0.5.0"
group = "hkmod"
val modid = "soluna"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

println(
    "Java: " + System.getProperty("java.version") + " JVM: " + System.getProperty("java.vm.version") + "(" + System.getProperty(
        "java.vendor"
    ) + ") Arch: " + System.getProperty("os.arch")
)

val Project.minecraft: MinecraftExtension
    get() = extensions.getByType()

minecraft.run {

    mappings("parchment", "2022.08.14-1.19.2")

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs.run {
        create("client") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")

            property("forge.logging.console.level", "debug")

            property("forge.enabledGameTestNamespaces", modid)

            property("terminal.jline", "true")

            property("log4j.configurationFile", "log4j2.xml")

            mods {
                create(modid) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("server") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")

            property("forge.logging.console.level", "debug")

            property("forge.enabledGameTestNamespaces", modid)

            property("terminal.jline", "true")

            mods {
                create(modid) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("gameTestServer") {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "REGISTRIES")

            property("forge.logging.console.level", "debug")

            property("forge.enabledGameTestNamespaces", modid)

            property("terminal.jline", "true")

            mods {
                create(modid) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("data") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")

            property("forge.logging.console.level", "debug")

            property("terminal.jline", "true")

            args(
                "--mod",
                modid,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources")
            )
            mods {
                create(modid) {
                    source(sourceSets.main.get())
                }
            }
        }

        all {
            lazyToken("minecraft_classpath") {
                return@lazyToken configurations.getByName("library").copyRecursive().resolve()
                    .joinToString(File.pathSeparator) { it.absolutePath }
            }
        }
    }
}

configurations {
    val library = maybeCreate("library")
    implementation.configure {
        extendsFrom(library)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("net.minecraftforge:forge:1.19.2-43.0.11")
}

sourceSets.main.configure {
    resources.srcDirs("src/generated/resources/")
}

tasks.withType<Jar> {
    archiveBaseName.set(modid)
    manifest {
        val map = HashMap<String, String>()
        map["Specification-Title"] = modid
        map["Specification-Vendor"] = "tmvkrpxl0, pleahmacaka"
        map["Specification-Version"] = "1"
        map["Implementation-Title"] = project.name
        map["Implementation-Version"] = archiveBaseName.get()
        map["Implementation-Vendor"] = "tmvkrpxl0, pleahmacaka"
        map["Implementation-Timestamp"] = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        attributes(map)
    }
    // finalizedBy("reobfJar")
}

fun DependencyHandler.minecraft(
    dependencyNotation: Any
): Dependency = add("minecraft", dependencyNotation)!!

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}