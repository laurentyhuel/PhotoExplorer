plugins {
    id("photoexplorer.android.library")
}

android {
    namespace = "com.lyh.photoexplorer.domain"
}

dependencies {
    testImplementation(libs.turbine)
}