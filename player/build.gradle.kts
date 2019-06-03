import com.android.tools.r8.kotlin.Kotlin

plugins {
    id(Android.libPlugin)
    id(Kotlin.androidPlugin)
    id(Kotlin.kapt)
}

dependencies {
    implementation(project(":data"))

    implementation(ExoPlayer.player)

    implementation(Firebase.core)
    implementation(Firebase.firestore)

    implementation(AndroidX.Arch.extensions)
    implementation(AndroidX.Arch.reactive_streams)
    kapt(AndroidX.Arch.compiler)

    implementation(Glide.core)
    kapt(Glide.compiler)
    implementation(Glide.transformations)

    implementation(AndroidX.Paging.common)
    implementation(AndroidX.Paging.runtime)
    implementation(AndroidX.Paging.rx)

    implementation(AndroidX.Ktx.core)
    implementation(AndroidX.Ktx.collection)
    implementation(AndroidX.Ktx.palette)
    implementation(AndroidX.Ktx.reactiveStreams)
    implementation(AndroidX.Ktx.sqlite)
    implementation(AndroidX.Ktx.viewmodel)

    implementation(KotlinX.Coroutines.core)
    implementation(KotlinX.Coroutines.android)
    implementation(KotlinX.Coroutines.rx)
    implementation(KotlinX.Serialization.dependency)

    implementation(RxJava.rxJava2)
    implementation(RxJava.rxAndroid)
    implementation(RxJava.rxKotlin)

    implementation(Kodein.runtime)
    implementation(Kodein.androidX)

    implementation(Timber.core)
    implementation(Gson.dependency)

    implementation(Retrofit.runtime)
    implementation(Retrofit.moshi)
    implementation(Retrofit.rxjava)

    implementation(OkHttp.core)
    implementation(OkHttp.loggingInterceptor)

    implementation(Moshi.dependency)

    testImplementation(AndroidX.Test.core)
    testImplementation(AndroidX.Test.junit)
    testImplementation(AndroidX.Test.rules)
    testImplementation(AndroidX.Arch.testing)
    testImplementation(AndroidX.Room.testing)
    testImplementation(Testing.Mockito.kotlin)
    testImplementation(Testing.PowerMock.core)
    testImplementation(Testing.PowerMock.api)
    testImplementation(Testing.PowerMock.module)
    testImplementation(RoboElectric.dependency)
}
