// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Add the dependency for the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2") // Android Gradle Plugin
        classpath("com.google.gms:google-services:4.4.2") // Google-Services-Plugin
    }
}
