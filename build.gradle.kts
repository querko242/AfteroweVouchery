import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    `java-library`
    `maven-publish`

    id("idea")
    id("org.ajoberstar.grgit") version "4.1.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

idea {
    project.jdkName = "17"
}

allprojects {
    group = "pl.aftermc"
    version = "1.0"

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        withSourcesJar()
        withJavadocJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<Javadoc> {
        options {
            (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet") // mute warnings
        }
    }


    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.aftermc.pl/releases")
    }
    dependencies {
        shadow("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
        shadow("pl.aftermc:afteroweapi:2.0")
    }


    publishing {
        repositories {
            maven {
                name = "aftermc"
                url = uri("https://repo.aftermc.pl/releases")
                credentials {
                    username = System.getenv("MAVEN_NAME")
                    password = System.getenv("MAVEN_TOKEN")
                }
            }
        }
        publications {
            create<MavenPublication>("library") {
                from(components.getByName("java"))

                // Add external repositories to published artifacts
                // ~ btw: pls don't touch this
                pom.withXml {
                    val repositories = asNode().appendNode("repositories")
                    project.repositories.findAll(closureOf<Any> {
                        if (this is MavenArtifactRepository && this.url.toString().startsWith("https")) {
                            val repository = repositories.appendNode("repository")
                            repository.appendNode("id", this.url.toString().replace("https://", "").replace("/", "-").replace(".", "-").trim())
                            repository.appendNode("url", this.url.toString().trim())
                        }
                    })
                }
            }
        }
    }
}

tasks.processResources {
    expand(
            "pluginVersion" to version,
            "pluginDescription" to "Plugin na vouchery | Pobrany z AfterMC.pl"
    )
}

tasks.withType<ShadowJar> {
    archiveFileName.set("AfteroweVouchery ${project.version}.jar")
}