plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.services)
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
    implementation(libs.androidx.core)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.google.fonts)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    testImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.arch.core.testing)

    androidTestImplementation(libs.androidx.test.corektx) {
        version {
            strictly("1.5.0")
        }
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-common")
    }
    testImplementation(libs.androidx.test.corektx) {
        version {
            strictly("1.5.0")
        }
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-common")
    }

    androidTestImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.core)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.testing)

    androidTestImplementation(libs.androidx.test.runner)
    testImplementation(libs.androidx.test.runner)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* *****************************************************
     **** Navigation compose
     ****************************************************** */
    implementation(libs.androidx.navigation.compose)

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

    /* *****************************************************
     **** Serialization
    ****************************************************** */
    implementation(libs.kotlinx.serialization.json)

    /* *****************************************************
     **** Firebase
    ****************************************************** */
    implementation(libs.com.google.firebase.analytics)

    /* *****************************************************
     **** Coil
    ****************************************************** */
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.test)
}