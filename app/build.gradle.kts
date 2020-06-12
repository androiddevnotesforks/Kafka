import com.android.build.gradle.BaseExtension

dependencies {
    implementation(project(Kafka.Data.nameDependency))
    implementation(project(Kafka.Domain.nameDependency))
    implementation(project(Kafka.Language.nameDependency))
    implementation(project(Kafka.UiCompose.nameDependency))
    implementation(project(Kafka.UiCommon.nameDependency))
    implementation(project(Kafka.Content.nameDependency))
    implementation(project(Kafka.Player.nameDependency))
    implementation(project(Kafka.Reader.nameDependency))

    implementation(Kotlin.stdlib)
    implementation(Jsoup.core)

    implementation(Store.core)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.fragment)
    implementation(AndroidX.drawerLayout)
    implementation(AndroidX.material)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.workManager)
    implementation(AndroidX.viewPager2)
    implementation(AndroidX.palette)
    implementation(AndroidX.appStartup)
    implementation(AndroidX.Paging.common)
    implementation(AndroidX.Paging.runtime)

    implementation(AndroidX.Navigation.fragment)
    implementation(AndroidX.Navigation.ui)

    implementation(AndroidX.Room.runtime)
    kapt(AndroidX.Room.compiler)

    implementation(Retrofit.runtime)

    implementation(AndroidX.Arch.extensions)
    implementation(AndroidX.Arch.reactive_streams)
    kapt(AndroidX.Arch.compiler)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
    implementation(Hilt.lifecycle)
    kapt(Hilt.lifecycle_compiler)

    compileOnly(AssistedInject.annotationDagger2)
    kapt(AssistedInject.processorDagger2)

    implementation(AndroidX.Ktx.core)
    implementation(AndroidX.Ktx.collection)
    implementation(AndroidX.Ktx.fragment)
    implementation(AndroidX.Ktx.palette)
    implementation(AndroidX.Ktx.reactiveStreams)
    implementation(AndroidX.Ktx.sqlite)
    implementation(AndroidX.Ktx.viewmodel)
    implementation(AndroidX.Ktx.lifecycle)
    implementation(AndroidX.Ktx.liveData)

    implementation(KotlinX.Coroutines.core)
    implementation(KotlinX.Coroutines.android)

    implementation(Lottie.core)
    implementation(Timber.core)
    implementation(Easeinterpolator.core)
    implementation(Coil.core)

    implementation(Stetho.core)
    implementation(Stetho.urlConnection)

    androidTestImplementation(AndroidX.annotation)
    androidTestImplementation(AndroidX.Test.junit)
    androidTestImplementation(AndroidX.Test.rules)
    androidTestImplementation(AndroidX.Espresso.core)
    androidTestImplementation(AndroidX.Espresso.intents)
    androidTestImplementation(AndroidX.Espresso.contrib)

    testImplementation(AndroidX.Arch.testing)
    testImplementation(AndroidX.Room.testing)
    testImplementation(Testing.Mockito.kotlin)
    testImplementation(Testing.PowerMock.core)
    testImplementation(Testing.PowerMock.api)
    testImplementation(Testing.PowerMock.module)
}

configure<BaseExtension> {
    dataBinding {
        this.isEnabled = true
    }

    defaultConfig {
        applicationId = Kafka.applicationId
    }
}

//apply(plugin = "com.google.gms.google-services")
