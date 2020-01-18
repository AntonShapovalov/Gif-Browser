package concept.gifbrowser.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide local (context) dependencies for [AppComponent]
 */
@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun providePreferences(): SharedPreferences =
        context.getSharedPreferences(context.packageName + ".settings", Context.MODE_PRIVATE)

}