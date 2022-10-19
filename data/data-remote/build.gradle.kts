import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("photoexplorer.android.library")
    id("kotlinx-serialization")
}

val localProperties = gradleLocalProperties(rootDir)

android {
    namespace = "com.lyh.photoexplorer.data.remote"

    defaultConfig {
        buildConfigField("String", "ACCESS_KEY", localProperties.getProperty("accessKey"))
    }
}

dependencies {
    implementation(libs.retrofit.core)

    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.datetime)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.serialization.json)
}