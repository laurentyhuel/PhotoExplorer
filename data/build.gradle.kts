plugins {
    id("photoexplorer.android.library")
}

android {
    namespace = "com.lyh.photoexplorer.data"
}

dependencies {

    implementation(libs.retrofit.core)
    implementation(project(":data:data-remote"))
    implementation(project(":domain"))

    testImplementation(libs.turbine)
}