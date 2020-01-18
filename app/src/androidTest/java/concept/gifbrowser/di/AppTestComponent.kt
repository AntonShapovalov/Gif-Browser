package concept.gifbrowser.di

import concept.gifbrowser.data.LocalDataAndroidTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides dependencies for [LocalDataAndroidTest]
 */
@Singleton
@Component(modules = [UtilsModule::class, AppModule::class])
interface AppTestComponent {

    fun inject(test: LocalDataAndroidTest)

}