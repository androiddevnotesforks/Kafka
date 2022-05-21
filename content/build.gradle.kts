plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Publishing.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Publishing.minSdkVersion)
        targetSdkVersion(Publishing.compileSdkVersion)
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Libs.Kotlin.version
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/ui-graphics_release.kotlin_module/")
        exclude("META-INF/*")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(platform(project(":data")))
    api(platform(project(":ui-common")))
    api(platform(project(":player")))
    api(platform(project(":logger")))
    api(platform(project(":reader")))
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Timber.core)

    implementation(Libs.KotlinX.Coroutines.core)
    implementation(Libs.KotlinX.Coroutines.android)

    implementation(Libs.Coil.core)
    implementation(Libs.ThreeTenBp.core)

    implementation(Libs.Retrofit.runtime)
    implementation(Libs.Retrofit.moshi)
    implementation(Libs.OkHttp.core)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation("com.google.android.play:core:1.8.2")

    implementation("com.pdftron:pdftron:7.1.4")
    implementation("com.pdftron:tools:7.1.4")

    implementation(Libs.Firebase.dynamicLinks)

    implementation(Libs.Hilt.android)
    kapt(Libs.Hilt.compiler)
    implementation(Libs.Hilt.lifecycle)
    kapt(Libs.Hilt.lifecycle_compiler)
    implementation(Libs.Hilt.workManager)

    implementation(Libs.material)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.fragment)
    implementation(Libs.AndroidX.Ktx.workManager)
    implementation(Libs.AndroidX.Ktx.sqlite)
    implementation(Libs.AndroidX.palette)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.datastore)

    implementation("androidx.core:core:1.5.0-alpha04")
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.Accompanist.coil)
    implementation("com.github.zsoltk:compose-router:0.20.0")

    implementation(Libs.AndroidX.Navigation.fragment)
    implementation(Libs.AndroidX.Navigation.ui)

    implementation(Libs.Lottie.core)
    implementation(Libs.Epoxy.core)
    implementation(Libs.Epoxy.databinding)
    kapt(Libs.Epoxy.processor)
}
