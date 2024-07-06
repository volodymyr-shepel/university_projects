plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    // I WAS TRYING TO MOVE IT TO libs.versions but it resulted in error
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.rogue.travelguru"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rogue.travelguru"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner  = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //exoplayer
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Retrofit with Kotlin serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")


    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")


    // Room
    implementation("androidx.room:room-ktx:2.5.1")
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-paging:2.5.1")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)

    // firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.google.play.services.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // Hilt
    implementation(libs.google.dagger.hilt.android)
    kapt(libs.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-extended-android")

    implementation(libs.google.maps)
    implementation(libs.google.play.services.location)
    implementation(libs.google.accompanist.permissions)

    implementation("androidx.compose.foundation:foundation-layout:1.7.0-alpha08")

    // testing

    implementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.compose.ui:ui-test-junit4:1.6.7")
    implementation("org.mockito:mockito-core:4.4.0")
    implementation("org.mockito:mockito-android:4.4.0")
    implementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    runtimeOnly("androidx.compose.ui:ui-test-manifest:1.6.7")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")

    androidTestImplementation("org.mockito:mockito-core:4.4.0")
    androidTestImplementation("org.mockito:mockito-android:4.4.0")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.mockito:mockito-android:4.4.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    testImplementation("androidx.compose.ui:ui-test-junit4:1.6.7")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("org.robolectric:robolectric:4.10.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")





}
kapt {
    correctErrorTypes = true
}