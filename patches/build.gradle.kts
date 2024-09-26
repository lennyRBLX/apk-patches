group = "patches.lennyrblx"

patches {
    about {
        name = "APK Patches"
        description = "Personal patches for various Android APKs"
        source = "git@github.com:lennyRBLX/apk-patches.git"
        author = "lennyRBLX"
        contact = ""
        website = ""
        license = "GNU General Public License v3.0"
    }
}

dependencies {
    // Used by JsonGenerator.
    implementation(libs.gson)
    // Required due to smali, or build fails. Can be removed once smali is bumped.
    implementation(libs.guava)
}

tasks {
    jar {
        exclude("app/revanced/generator")
    }

    register<JavaExec>("generatePatchesFiles") {
        description = "Generate patches files"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.revanced.generator.MainKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesFiles")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lennyRBLX/apk-patches")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
