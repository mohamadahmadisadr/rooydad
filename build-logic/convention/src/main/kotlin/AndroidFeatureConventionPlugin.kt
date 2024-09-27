import app.rooydad.android.compose.configureAndroidCompose
import app.rooydad.android.compose.configureKotlinAndroid
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {

      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

      pluginManager.apply {
        apply("com.android.library")
//        apply("com.android.application")
        apply("app.rooydad.app.android.hilt")
        apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
      }




      dependencies {
        add("implementation", project(":core:data"))
        add("compileOnly", project(":core:designsystem"))
        add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        configureAndroidCompose(this)
        defaultConfig.targetSdk = 34
      }

//      extensions.getByType<KotlinAndroidProjectExtension>().apply {
//        configureKotlinAndroid(this)
//      }
    }
  }
}
