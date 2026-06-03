plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.watchface"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.watchface"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(platform(libs.compose.bom))

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)

    implementation(libs.play.services.wearable)
    /*implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)*/
    implementation(libs.runtime)
    implementation(libs.foundation.layout)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.1")

   /* implementation("androidx.activity:activity-compose:...")
    implementation("androidx.compose.ui:ui:...")
    implementation("androidx.compose.material3:material3:1.4.0")*/
}