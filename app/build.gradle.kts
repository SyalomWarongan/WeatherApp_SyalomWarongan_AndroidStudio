plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.weatherapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {

        dataBinding = (true)

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

    lint {
        baseline = file("lint-baseline.xml")


    }

    dependencies {

        implementation("androidx.core:core-ktx:1.9.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        implementation("com.google.code.gson:gson:2.9.0")
        implementation("com.squareup.retrofit2:retrofit:2.8.0")
        implementation("com.squareup.retrofit2:converter-gson:2.8.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

        implementation("io.reactivex.rxjava3:rxjava:3.1.5")
        implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
        implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

        implementation("com.google.android.material:material:1.9.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

        implementation("com.github.bumptech.glide:glide:4.12.0")
        annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

        implementation("com.google.android.gms:play-services-location:21.0.1")

    }
}