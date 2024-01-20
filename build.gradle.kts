// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
       set("splashscreen_version" , "1.0.0")
       set("hilt_version" , "2.48")
       set("viewModel_version" , "2.7.0")
       set("nav_version" , "2.7.5")
       set("glide_version" , "4.16.0")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

