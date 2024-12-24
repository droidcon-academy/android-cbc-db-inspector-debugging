buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:8.7.3")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "8.7.3" apply false
    id ("com.android.library") version "8.7.3" apply false
    id ("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id ("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
}