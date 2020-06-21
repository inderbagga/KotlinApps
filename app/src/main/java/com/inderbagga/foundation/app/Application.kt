package com.inderbagga.foundation.app

import android.app.Application
import com.inderbagga.foundation.BuildConfig
import com.inderbagga.foundation.app.modules.gitClientApiModule
import com.inderbagga.foundation.app.modules.gitRemoteApiModule
import com.inderbagga.foundation.app.modules.mainViewModel
import com.inderbagga.foundation.app.modules.repoListDataSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Inder Bagga on 20/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@KotlinApplication)
            modules(listOf(
                mainViewModel,
                gitRemoteApiModule,
                gitClientApiModule,
                repoListDataSourceFactory
            ))
        }
    }
}