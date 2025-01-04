plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Google-Services-Plugin
}

android {
    namespace = "com.example.shroomsmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shroomsmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase Bill of Materials (BoM) für die Verwaltung der Firebase-Bibliotheken
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Firebase-Bibliotheken: Versionen werden durch die BoM automatisch festgelegt
    implementation("com.google.firebase:firebase-auth")       // Firebase-Authentifizierung
    implementation("com.google.firebase:firebase-firestore")  // Firebase Cloud Firestore
    implementation("com.google.firebase:firebase-crashlytics") // Firebase Crashlytics für Fehlerberichte
    implementation("com.google.firebase:firebase-analytics")  // Firebase Analytics
}