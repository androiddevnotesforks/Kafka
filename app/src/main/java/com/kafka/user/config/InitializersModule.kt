package org.rekhta.user.config


import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kafka.data.AppInitializer
import com.kafka.data.injection.ProcessLifetime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.rekhta.base.AppCoroutineDispatchers
import org.threeten.bp.zone.ZoneRulesProvider
import radiography.Radiography
import radiography.ViewStateRenderers.DefaultsIncludingPii
import timber.log.Timber
import javax.inject.Inject

class LoggerInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        Timber.plant(Timber.DebugTree())
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (priority == Log.ASSERT) {
                    t?.let { throw it }
                }
            }
        })
    }
}

class FirebaseInitializer @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    @ProcessLifetime private val coroutineScope: CoroutineScope
) : AppInitializer {
    override fun init(application: Application) {
        coroutineScope.launch(dispatchers.io) {
            Firebase.initialize(application)
            FirebaseApp.initializeApp(application)
        }
    }
}

class ThreeTenBpInitializer @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    @ProcessLifetime private val coroutineScope: CoroutineScope
) : AppInitializer {
    override fun init(application: Application) {
        coroutineScope.launch(dispatchers.io) {
            AndroidThreeTen.init(application)
            ZoneRulesProvider.getAvailableZoneIds()
        }
    }
}

class FlipperInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
//        SoLoader.init(application, false)
//
//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(application)) {
//            val client = AndroidFlipperClient.getInstance(application)
//            client.addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
//            client.addPlugin(DatabasesFlipperPlugin(application))
//            client.start()
//        }
    }
}

class RadiographyInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        Radiography.scan(viewStateRenderers = DefaultsIncludingPii)
    }
}
