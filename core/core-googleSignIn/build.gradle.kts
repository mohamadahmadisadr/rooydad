plugins {
    id("app.rooydad.app.android.library")
    id("app.rooydad.app.android.hilt")
    id("app.rooydad.app.spotless")
//    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.rooydad.app.coreGooglesignin"

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}