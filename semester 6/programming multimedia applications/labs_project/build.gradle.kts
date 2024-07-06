buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.hilt.android.gradle.plugin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}