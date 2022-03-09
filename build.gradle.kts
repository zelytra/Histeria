plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.3.3"
}
allprojects {
    repositories {
        jcenter()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://papermc.io/repo/repository/maven-public/")
            url = uri("https://jitpack.io")
            url = uri("https://oss.sonatype.org/content/groups/public/")
            url = uri("https://maven.enginehub.org/repo/")
            url = uri("https://repo.maven.apache.org/maven2/")
        }

    }
}

dependencies {
    compileOnly("com.github.SaberLLC:Saber-Factions:2.4.0-RC") {
        exclude("mkremins", "fanciful")
        exclude("me.rayzr522", "jsonmessage")
        exclude("org.bstats", "bstats-bukkit")
    }
    compileOnly("com.sk89q.worldedit:worldedit-core:7.3.0-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.0-SNAPSHOT")
    compileOnly("net.luckperms:api:5.3")
    paperDevBundle("1.18.1-R0.1-SNAPSHOT")
    //compileOnly("com.github.zelytra:MoreStats:0.0.6")
}

group = "fr.zelytra"
version = "3.0"
description = "Histeria"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
