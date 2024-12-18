plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.cyber"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cyber"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Firebase BoM (gerencia versões automaticamente)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0")) // Versao de documentação do Firebase
    // Firebase Auth (não precisa especificar versão quando usa o BoM)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    // implementation("com.google.android.gms:play-services:12.0.1")
    // Jetpack Compose Core'
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Compose Material e Material 3
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Outros
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation(libs.firebase.auth)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.firebase.crashlytics.buildtools)

    // Testes
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")

    // Ferramentas de Desenvolvimento do Compose
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")

    // OkHttp para requisições HTTP
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.gson)

}
