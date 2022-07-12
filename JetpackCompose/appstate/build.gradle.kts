plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.appstate"
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
        viewBinding = true
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
    implementation(Compose.foundation)
    implementation(Compose.runtimeLivedata)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.foundationLayout)
    implementation(Compose.constraintLayout)
    implementation(Compose.materialIconExtended)
    debugImplementation(Compose.uiTooling)

    implementation(Lifecycle.runtimeKtx)
    implementation(Lifecycle.livedataKtx)
    implementation(Lifecycle.viewModelKtx)
    implementation(Lifecycle.viewModelCompose)
    implementation(Lifecycle.viewModelSavedState)
}
