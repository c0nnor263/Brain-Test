package com.gamovation.tilecl.di.startup

import android.content.Context
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class MobileAdsInitializer : Initializer<MobileAdsInitializer> {
    private val configuration = RequestConfiguration.Builder()
        .setTestDeviceIds(
            listOf(
                "605294fe-166c-4a6f-a59e-f35f680865f3"
            )
        )
        // TODO: Add your test device ID here
        .build()

    override fun create(context: Context): MobileAdsInitializer {
        return MobileAdsInitializer().also {
            MobileAds.initialize(context)
            MobileAds.setRequestConfiguration(configuration)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}