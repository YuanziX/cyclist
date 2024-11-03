package dev.yuanzix.cyclist

import android.app.Application
import dev.yuanzix.cyclist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CyclistApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CyclistApp)
            androidLogger()

            modules(appModule)
        }
    }
}