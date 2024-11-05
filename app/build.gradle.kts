plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "co.wawand.composetypesafenavigation"
    compileSdk = extra["compileSDK"] as Int

    defaultConfig {
        applicationId = "co.wawand.composetypesafenavigation"
        minSdk = extra["minSDK"] as Int
        targetSdk = extra["targetSDK"] as Int
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
        sourceCompatibility = extra["javaVersion"] as JavaVersion
        targetCompatibility = extra["javaVersion"] as JavaVersion
    }

    kotlinOptions {
        jvmTarget = extra["kotlinJvmTarget"] as String
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.google.fonts)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* *****************************************************
     **** Lifecycle
     ****************************************************** */
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.activity.ktx)

    /* *****************************************************
      **** Coroutines
      ****************************************************** */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    /* *****************************************************
      **** Splash
      ****************************************************** */
    implementation(libs.androidx.core.splashscreen)

    /* *****************************************************
    **** Dependency-Hilt Injection
    ****************************************************** */
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.work)

    /* *****************************************************
      **** Retrofit2
      ****************************************************** */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)

    /* *****************************************************
      **** Room database
      ****************************************************** */
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    /* *****************************************************
      **** DataStore
      ****************************************************** */
    implementation(libs.androidx.datastore.preferences)

    /* *****************************************************
     **** Worker
    ****************************************************** */
    implementation(libs.androidx.work.runtime.ktx)
}