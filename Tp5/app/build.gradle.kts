plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.php2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.php2"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.common)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.airbnb.android:lottie:6.5.2")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.google.code.gson:gson:2.10.1")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
}