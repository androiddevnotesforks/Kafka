package com.kafka.gradle.extentions

import java.lang.System.getenv

object Configurations {

    object Android {
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val compileSdkVersion = 31
    }

    object Keys {
        const val nearby = """"AIzaSyAG5OPz9DPRhBwyaaPsAuqqim3GabOfKRg""""
        const val mapbox =
            """"pk.eyJ1IjoibGV2aWNvbGUiLCJhIjoiY2o4eXFuN2k1MXd5azMzcjAyaXRyZGR2ZSJ9.UT4ZE_XQZ6Ri87OD6ACdoQ""""
        const val ablyMapBox =
            """"pk.eyJ1IjoiYWJseS1kaC1hc3NldC10cmFja2luZyIsImEiOiJja25xOTltMDIxdm9sMnFueG5hcjRxa2ZuIn0.eZApDvmMMIRSASHvwHf3Lg""""
        const val ably =
            """"W4mfgw.ar3s4w:ciNkYHkbm9pqcy2g""""
        const val google = """"AIzaSyDl3BruHWokbXx28AdDTjdAvcWRIba20a8""""
    }

    object App {
        const val applicationIdentifier = "com.foodora.courier"
    }

    object KeyStore {
        const val debugKeystorePath = "../debug.keystore"
    }

    object AppCenter {
        val appCenterSecretDev = getenv("APPCENTER_SECRET_DEV") ?: ""
        val appCenterSecretRelease = getenv("APPCENTER_SECRET_RELEASE") ?: ""
        val appCenterSecretRC = getenv("APPCENTER_SECRET_RC") ?: ""
        val appCenterSecretWoowaBrothersDev = getenv("APPCENTER_SECRET_WOOWABROTHERS_DEV") ?: ""
        val appCenterSecretWoowaBrothers = getenv("APPCENTER_SECRET_WOOWABROTHERS") ?: ""
    }

    object Pelican {
        const val pelicanUrlStaging =
            "https://install.appcenter.ms/orgs/pelican-dh/apps/pelican-staging/distribution_groups/public"
        const val pelicanUrlRelease =
            "https://install.appcenter.ms/orgs/pelican-dh/apps/pelican/distribution_groups/public"
        const val pelicanPackageNameStaging = "com.deliveryhero.global.pelican.dev"
        const val pelicanPackageNameRelease = "com.deliveryhero.global.pelican"
    }

    object TestRail {
        const val PROJECT_ID = 4
        const val SUITE_ID = 12
        const val CREATOR_ID = 1180
        const val SMOKE_TEST_TYPE_ID = 11
        const val BASE_URL = "https://deliveryhero.testrail.io/"
        const val ADD_RESULT_ENDPOINT = "index.php?/api/v2/add_result_for_case/"
        val username = getenv("TESTRAIL_USERNAME") ?: ""
        val password = getenv("TESTRAIL_PASSWORD") ?: ""
    }

    object Flank {
        const val IS_CLEAR_PACKAGE_DATA = "clearPackageData"
        const val REPEAT_TEST_COUNT = "repeatTestCount"
        const val LISTENERS = "listener"
        val ENVIRONMENT_VARIABLES = mapOf(
            IS_CLEAR_PACKAGE_DATA to "true",
            LISTENERS to "com.roadrunner.lib.test.ui.util.runlistener.CrashingRunListener," +
                    "com.roadrunner.lib.test.ui.util.runlistener.ToastingRunListener," +
                    "com.roadrunner.lib.test.ui.util.runlistener.TracingRunListener"
        )
    }
}
