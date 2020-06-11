package com.kafka.user.injection

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.data.base.CrashLogger
import com.kafka.data.injection.ApplicationContext
import com.kafka.data.injection.ApplicationId
import com.kafka.data.injection.Initializers
import com.kafka.data.injection.ProcessLifetime
import com.kafka.user.util.CrashlyticsCrashLogger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.Multibinds
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI module that provides objects which will live during the application lifecycle.
 */

@InstallIn(ApplicationComponent::class)
@Module(includes = [InitializersModule::class, AppModuleBinds::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideGeneralUseContext(@ApplicationContext appContext: Context): Context =
        ContextWrapper(appContext)

    @ApplicationId
    @Provides
    fun provideApplicationId(application: Application): String = application.packageName

    @Named("app")
    @Provides
    @Singleton
    fun provideAppPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @ProcessLifetime
    fun provideLongLifetimeScope(): CoroutineScope {
        return ProcessLifecycleOwner.get().lifecycle.coroutineScope
    }
}

@InstallIn(ApplicationComponent::class)
@Module
abstract class AppModuleBinds {
    @Binds
    @ApplicationContext
    @Singleton
    abstract fun provideApplicationContext(application: Application): Context

    @Initializers
    @Multibinds
    abstract fun initializers(): Set<() -> Unit>

    @Binds
    abstract fun bindCrashLogger(bind: CrashlyticsCrashLogger): CrashLogger
}
