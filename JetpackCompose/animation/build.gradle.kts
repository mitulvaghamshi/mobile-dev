plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.animations"
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
    }
}

dependencies {
    implementation(AndroidX.appcompat)

    implementation(Lifecycle.runtimeKtx)

    implementation(Compose.ui)
    implementation(Compose.activity)
    implementation(Compose.material)
    implementation(Compose.uiToolingPreview)
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)

    testImplementation(Testing.jUnit)
    testImplementation(Testing.extJUnit)
    testImplementation(Testing.googleTruth)

    androidTestImplementation(Compose.uiTestJUnit4)
    androidTestImplementation(Testing.espressoCore)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=org.mylibrary.OptInAnnotation"
}