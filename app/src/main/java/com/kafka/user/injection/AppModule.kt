package com.kafka.user.injection

import android.app.Application
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kafka.data.AppInitializer
import com.kafka.data.injection.ProcessLifetime
import com.kafka.user.config.FirebaseInitializer
import com.kafka.user.config.LoggerInitializer
import com.kafka.user.config.ThreeTenBpInitializer
import com.kafka.user.deeplink.FirebaseDynamicDeepLinkHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.kafka.analytics.CrashLogger
import org.kafka.analytics.FirebaseCrashLogger
import org.kafka.analytics.FirebaseLogger
import org.kafka.analytics.Logger
import org.kafka.base.AppCoroutineDispatchers
import org.kafka.common.image.CoilAppInitializer
import org.kafka.navigation.DynamicDeepLinkHandler
import org.kafka.notifications.NotificationManager
import org.kafka.notifications.NotificationManagerImpl
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI module that provides objects which will live during the application lifecycle.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @ProcessLifetime
    fun provideLongLifetimeScope(): CoroutineScope {
        return ProcessLifecycleOwner.get().lifecycleScope
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(app: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(app).apply {
            setUserId("")
        }
    }

    @Named("app")
    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context) = context.dataStore

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Singleton
    @Provides
    fun provideNotificationManagerImpl(application: Application) = NotificationManagerImpl(
        application,
        NotificationManagerCompat.from(application.applicationContext)
    )
}

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Binds
    abstract fun bindLogger(firebaseLogger: FirebaseLogger): Logger

    @Binds
    abstract fun bindCrashLogger(firebaseCrashLogger: FirebaseCrashLogger): CrashLogger

    @Binds
    @IntoSet
    abstract fun provideThreeTenAbpInitializer(bind: ThreeTenBpInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideLoggerInitializer(bind: LoggerInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideFirebaseInitializer(bind: FirebaseInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideCoilAppInitializer(bind: CoilAppInitializer): AppInitializer

    @Singleton
    @Binds
    abstract fun provideNotificationManager(bind: NotificationManagerImpl): NotificationManager

    @Binds
    abstract fun deepLinkHandler(firebaseDynamicDeepLinkHandler: FirebaseDynamicDeepLinkHandler): DynamicDeepLinkHandler
}
