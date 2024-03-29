// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    extra.apply {
        set("splashscreen_version", "1.0.1")
        set("hilt_version", "2.48")
        set("viewModel_version", "2.7.0")
        set("nav_version", "2.7.7")
        set("glide_version", "4.16.0")
        set("okhttp_version", "4.10.0")
        set("retrofit_version", "2.9.0")
        set("room_version", "2.6.1")
        set("paging_version", "3.2.1")
        set("shimmer_version", "0.5.0")
        set("swipe_version", "1.1.0")
        set("lottie_version", "6.0.0")
    }

    dependencies {
        val navVersion = rootProject.extra["nav_version"]
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

