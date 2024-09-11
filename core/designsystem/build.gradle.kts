plugins {
    id("app.rooydad.app.android.library")
    id("app.rooydad.app.android.library.compose")
    id("app.rooydad.app.spotless")
}

android {
    namespace = "com.rooydad.core.designsystem"
}
dependencies{
//    api(libs.landscapist.glide)
//    api(libs.landscapist.animation)
//    api(libs.landscapist.placeholder)
//    api(libs.landscapist.palette)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.ui.tooling)
    api(libs.androidx.material3)
}


























dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}