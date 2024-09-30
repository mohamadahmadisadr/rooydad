plugins {
    id("app.rooydad.app.android.feature")
}

android {
    namespace = "app.rooydad.feature.featureProfile"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.compose)
}