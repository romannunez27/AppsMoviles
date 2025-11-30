plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Agregar esto para habilitar KAPT
    kotlin("kapt")
}

android {
    namespace = "com.example.pasteleriamilsaboresapp"
    compileSdk {
        version = release(36)
    }
    tasks.withType<Test> {
        useJUnitPlatform() // <<< NECESARIO
        testLogging {
            events("passed", "failed", "skipped")
        }
    }
    defaultConfig {
        applicationId = "com.example.pasteleriamilsaboresapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\kathe\\keystore\\miapp-release.jks")
            storePassword = "uwuowo"
            keyAlias = "frktro"
            keyPassword = "uwuowo"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // ✅ Material y Material3
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3:1.3.0")

    // ⚠️ No repitas esta línea si ya tienes la anterior, elimina una de estas dos:
    /// implementation(libs.androidx.material3)   ← esta puedes comentarla o borrar
    /// para evitar conflictos de versiones

    // 🖼️ Imágenes y coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // 🗺️ Mapa (MapLibre)
    implementation("org.maplibre.gl:android-sdk:11.5.0")

    // 🚀 Navegación Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 🧩 Room (Base de datos local)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // 🧠 LiveData runtime (si lo usas en ViewModels)
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    // 🕒 Manejo de fechas (si no usas java.time directamente)
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")

    // 📸 CameraX
    val camerax_version = "1.3.3"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")

    // 📱 Escáner QR y ML Kit
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // 🧪 Testing
    testImplementation(libs.junit)
    // Kotest (solo estas 2 son necesarias)
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
// MockK
    testImplementation("io.mockk:mockk:1.13.10")
// Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
// AndroidX Test
    testImplementation("androidx.arch.core:core-testing:2.2.0")
// JUnit 5 (solo engine, Kotest usa JUnit 5)
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

}