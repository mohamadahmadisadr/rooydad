import com.rooydad.app.Configuration

plugins {
    id("app.rooydad.app.android.library")
//    id("app.rooydad.android.hilt")
    id("app.rooydad.app.spotless")
}
android {
    namespace = "com.rooydad.core.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}