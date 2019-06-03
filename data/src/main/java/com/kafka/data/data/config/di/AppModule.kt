package com.kafka.data.data.config.di

import androidx.room.Room
import com.kafka.data.data.config.initializers.AppInitializer
import com.kafka.data.data.config.initializers.AppInitializers
import com.kafka.data.data.config.logging.Logger
import com.kafka.data.data.config.logging.TimberInitializer
import com.kafka.data.data.config.logging.TimberLogger
import com.kafka.data.data.api.ArchiveService
import com.kafka.data.data.api.RetrofitProvider.provideDefaultRetrofit
import com.kafka.data.data.api.RetrofitRunner
import com.kafka.data.data.db.DatabaseTransactionRunner
import com.kafka.data.data.db.MiddlewareDb
import com.kafka.data.data.db.RoomTransactionRunner
import com.kafka.data.data.sharedPrefs.UserPreferenceManager
import com.kafka.data.util.AppCoroutineDispatchers
import com.kafka.data.util.AppRxSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * @author Vipul Kumar; dated 11/12/18.
 */
val appModule = Kodein.Module("appModule") {

    bind<DatabaseTransactionRunner>() with singleton {
        RoomTransactionRunner(instance())
    }

    bind<MiddlewareDb>() with singleton {
        Room.databaseBuilder(instance(), MiddlewareDb::class.java, "ArchiveService.db").build()
    }

    bind<Logger>() with singleton {
        TimberLogger()
    }

    bind<ArchiveService>() with singleton {
        provideMiddleware(retrofit = provideDefaultRetrofit(instance()))
    }

    bind<AppInitializer>("TimberInitializer") with singleton {
        TimberInitializer(instance())
    }

    bind<AppInitializers>() with singleton {
        AppInitializers()
    }

    bind<RetrofitRunner>() with provider {
        RetrofitRunner()
    }

    bind<AppRxSchedulers>() with provider {
        AppRxSchedulers(
            io = Schedulers.io(),
            computation = Schedulers.computation(),
            main = AndroidSchedulers.mainThread()
        )
    }

    bind<AppCoroutineDispatchers>() with provider {
        AppCoroutineDispatchers(
            io = instance<AppRxSchedulers>().io.asCoroutineDispatcher(),
            computation = instance<AppRxSchedulers>().computation.asCoroutineDispatcher(),
            main = Dispatchers.Default
        )
    }

    bind<UserPreferenceManager>() with singleton {
        UserPreferenceManager(instance())
    }
}

fun provideMiddleware(retrofit: Retrofit): ArchiveService {
    return retrofit
        .create(ArchiveService::class.java)
}
