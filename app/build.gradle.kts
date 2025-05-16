plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.zoteromvp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.zoteromvp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    configurations {
        create("cleanedAnnotations")
        implementation {
            exclude(group = "org.jetbrains", module = "annotations")
        }
    }

    useLibrary("org.apache.http.legacy")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.strict.version.matcher.plugin)
    implementation(libs.annotation)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // New rows

    //implementation (libs.retrofit)
    implementation (libs.okhttp3.okhttp)
    implementation (libs.converter.gson)


    // libzotero
    implementation (libs.squareup.retrofit)
    implementation (libs.rxjava.core)
    implementation (libs.annotations)
    implementation (libs.okhttp)
    implementation (libs.okhttp.urlconnection)

    // testImplementation (libs.junit.v411)
    testImplementation (libs.assertj.core)
}