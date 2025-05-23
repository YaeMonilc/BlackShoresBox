import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.symbol.processing)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.protobuf)
}

android {
    namespace = "ltd.aliothstar.blackshoresbox"
    compileSdk = 36

    defaultConfig {
        applicationId = "ltd.aliothstar.blackshoresbox"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0-Alpha"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //Coil
    implementation(libs.coil.compose)

    //Datastore
    implementation(libs.androidx.datastore.core)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    //Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    //Material icon extended
    implementation(libs.androidx.material.icons.extended)

    //Okhttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //Protobuf
    implementation(libs.protobuf.javalite)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Webkit
    implementation(libs.androidx.webkit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}