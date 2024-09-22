import org.gradle.kotlin.dsl.android

plugins {
    id("app.rooydad.app.android.feature")
    id("app.rooydad.app.android.hilt")

}

android {
    namespace = "com.rooydad.feature.login"
}

dependencies{
    implementation(project(":feature:feature-googleAuth"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.compose)
}