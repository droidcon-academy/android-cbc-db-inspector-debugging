plugins {
    id("kotlin-android")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose") version ("2.0.0")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.droidcon.easyinvoice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.droidcon.easyinvoice"
        minSdk = 21
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.compose.animation:animation:1.5.4")
    implementation ("androidx.compose.ui:ui-tooling:1.5.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation ("com.google.dagger:hilt-android:2.51.1")
    ksp ("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")

    implementation ("com.google.accompanist:accompanist-insets-ui:0.24.7-alpha")

}