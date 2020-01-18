package concept.gifbrowser.app

import android.app.Application
import concept.gifbrowser.BuildConfig
import concept.gifbrowser.di.*
import timber.log.Timber

class GBApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .utilsModule(UtilsModule())
            .apiModule(ApiModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}