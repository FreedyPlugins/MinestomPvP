plugins {
    java
    `maven-publish`
}

group = "io.github.bloepiloepi"
version = "1.0"

repositories {
    mavenCentral()

    maven { url = uri("https://repo.spongepowered.org/maven") }
    maven { url = uri("https://repo.velocitypowered.com/snapshots/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.minestom.net/repository/maven-public/") }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.bloepiloepi"
            artifactId = "minestompvp"
            version = "test"
            version = System.getenv()["GITHUB_BUILD_NUMBER"]
            val artifactList = ArrayList<PublishArtifact>()
            File("build/libs/").listFiles { file, s -> s.endsWith(".jar") }
                    ?.forEach { artifacts { artifactList.add(add("archives", it)) } }
            setArtifacts(artifactList)
        }
    }
    repositories {
        maven {
            name = "Packages"
//            url = uri("/Users/ijong-won/IdeaProjects/Minestom/published")
            url = uri("https://maven.pkg.github.com/%s".format(System.getenv()["GITHUB_REPOSITORY"]))
            credentials {
                this.username = System.getenv()["GITHUB_REPOSITORY"]?.split("/")?.get(0)
                this.password = System.getenv()["GITHUB_TOKEN"]
            }
        }
    }
}

dependencies {
    compileOnly("com.github.Minestom.Minestom:Minestom:9b15acf4fa")
    testImplementation("com.github.Minestom.Minestom:Minestom:9b15acf4fa")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
