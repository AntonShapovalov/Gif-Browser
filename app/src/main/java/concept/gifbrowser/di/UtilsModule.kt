package concept.gifbrowser.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide utils (not context) dependencies
 */
@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

}