// Top-level build file where you can add configuration options common to all sub-projects/modules.
// build.gradle.kts (Projeto)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

}
buildscript {





    dependencies {

        classpath ("com.google.gms:google-services:4.4.2")
        //classpath (libs.gms.google.services.v440)
    }
}