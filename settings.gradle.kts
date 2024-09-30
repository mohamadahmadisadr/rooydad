enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("androidx\\..*")
                includeGroupByRegex("com\\.android(\\..*|)")
                includeGroupByRegex("com\\.google\\.android\\..*")
                includeGroupByRegex("com\\.google\\.firebase(\\..*|)")
                includeGroupByRegex("com\\.google\\.gms(\\..*|)")
                includeGroupByRegex("com\\.google\\.mlkit")
                includeGroupByRegex("com\\.google\\.oboe")
                includeGroupByRegex("com\\.google\\.prefab")
                includeGroupByRegex("com\\.google\\.testing\\.platform")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        mavenCentral() {
            content {
                includeGroup("com.google.dagger")
                includeGroup("com.google.dagger.hilt.android")
            }
            mavenContent {
                releasesOnly()
            }
        }
    }


}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // fetch libraries from google maven (https://maven.google.com)
        google() {
            content {
                includeGroupByRegex("androidx\\..*")
                includeGroupByRegex("com\\.android(\\..*|)")
                includeGroupByRegex("com\\.google\\.android\\..*")
                includeGroupByRegex("com\\.google\\.firebase(\\..*|)")
                includeGroupByRegex("com\\.google\\.gms(\\..*|)")
                includeGroupByRegex("com\\.google\\.mlkit")
                includeGroupByRegex("com\\.google\\.oboe")
                includeGroupByRegex("com\\.google\\.prefab")
                includeGroupByRegex("com\\.google\\.testing\\.platform")
            }
            mavenContent {
                releasesOnly()
            }
        }

        // fetch libraries from maven central
        mavenCentral() {
            mavenContent {
                releasesOnly()
            }
        }

        // fetch snapshot libraries from sonatype
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

rootProject.name = "rooydad"
include(":app")
include(":core")
include(":feature")
include(":core:data")
include(":feature:feature-googleAuth")
include(":feature:feature-login")
include(":core:designsystem")
include(":feature:feature-profile")
