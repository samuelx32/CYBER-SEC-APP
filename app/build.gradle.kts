plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.cyber"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cyber"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.1"  // Certifique-se de que está usando a versão correta
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Dependências do Compose
    implementation ("androidx.compose.material:material-icons-extended:1.7.5")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")  // Para navegação com Compose

    implementation (platform(libs.firebase.bom.v3231))
    implementation(libs.firebase.ui.auth)
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Core Android e Lifecycle
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.compose.material:material-icons-core:1.4.3")
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")
    implementation ("androidx.compose.foundation:foundation:1.4.3")
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.compose.material:material-icons-core:1.4.3")
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")
    implementation ("androidx.compose.animation:animation:1.4.3")
    implementation ("io.coil-kt:coil-compose:2.2.2")




    // FirebaseUI



    // Testes
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")

    // Ferramentas de Desenvolvimento do Compose
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0")
}

