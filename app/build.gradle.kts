import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

val keyProperties = Properties()
val keyPropertiesFile = rootProject.file("local.properties")
if (keyPropertiesFile.exists()) {
    keyProperties.load(FileInputStream(keyPropertiesFile))
}

android {
    namespace = "com.example.moneyforward"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moneyforward"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", keyProperties["baseUrl"].toString())
        buildConfigField("String", "BASE_GITHUB_URL", keyProperties["gitHubBaseUrl"].toString())
        buildConfigField("String", "GIT_HUB_PERSONAL_TOKEN", keyProperties["gitHubPersonalToken"].toString())
    }
    buildFeatures{
        dataBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.koin.android)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.multidex)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.guava)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    // JUnit
    testImplementation(libs.junit)

// Coroutines test
    testImplementation(libs.kotlinx.coroutines.test)

// LiveData test helper
    testImplementation(libs.androidx.core.testing)

// MockK
    testImplementation(libs.mockk)
    testImplementation(kotlin("test"))

    // Core Coroutines
    implementation(libs.kotlinx.coroutines.core)

// For Android Main dispatcher
    implementation(libs.kotlinx.coroutines.android)

// Coroutines Test (for unit tests)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("com.google.android.material:material:1.1.0")

    implementation("com.makeramen:roundedimageview:2.3.0")
}