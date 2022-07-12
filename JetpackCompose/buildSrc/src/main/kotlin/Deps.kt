object Versions {
    const val agpVersion = "7.2.1"
    const val kotlinVersion = "1.6.21"
}

object Testing {
    val jUnit by lazy { "junit:junit:4.13.2" }
    val extJUnit by lazy { "androidx.test.ext:junit:1.1.3" }
    val googleTruth by lazy { "com.google.truth:truth:1.1.3" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:3.4.0" }
}

object AndroidX {
    val coreKtx by lazy { "androidx.core:core-ktx:1.7.0" }
    val appcompat by lazy { "androidx.appcompat:appcompat:1.4.2" }
    val material by lazy { "com.google.android.material:material:1.2.0-beta03" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:2.1.4" }
}

object Lifecycle {
    private const val lifecycleVersion = "2.4.1"
    val runtimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion" }
    val livedataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion" }
    val viewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion" }
    val viewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion" }
    val viewModelSavedState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion" }
}

object Compose {
    const val version = "1.2.0-rc01"

    val ui by lazy { "androidx.compose.ui:ui:$version" }
    val uiTooling by lazy { "androidx.compose.ui:ui-tooling:$version" }
    val material by lazy { "androidx.compose.material:material:$version" }
    val foundation by lazy { "androidx.compose.foundation:foundation:$version" }
    val uiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:$version" }
    val runtimeLivedata by lazy { "androidx.compose.runtime:runtime-livedata:$version" }
    val foundationLayout by lazy { "androidx.compose.foundation:foundation-layout:$version" }
    val materialIconExtended by lazy { "androidx.compose.material:material-icons-extended:$version" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout-compose:1.0.1" }
    val activity by lazy { "androidx.activity:activity-compose:1.4.0" }
    val compiler by lazy { "androidx.compose.compiler:compiler:1.1.1" }
    val coil by lazy { "io.coil-kt:coil-compose:2.1.0" }

    val uiTestJUnit4 by lazy { "androidx.compose.ui:ui-test-junit4:$version" }
    val uiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:$version" }
}
