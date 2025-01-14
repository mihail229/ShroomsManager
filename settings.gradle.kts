pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Verhindert Repositories in Modul-spezifischen build.gradle.kts
    repositories {
        google() // Google-Repository für Android- und Firebase-Abhängigkeiten
        mavenCentral() // Maven-Repository für allgemeine Java/Kotlin-Bibliotheken
    }
}

rootProject.name = "ShroomsManager"
include(":app")
