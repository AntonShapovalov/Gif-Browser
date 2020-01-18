package concept.gifbrowser.di

import concept.gifbrowser.data.LocalDataTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides dependencies for [LocalDataTest]
 */
@Singleton
@Component(modules = [UtilsModule::class, AppModule::class])
interface AppTestComponent {

    fun inject(test: LocalDataTest)

}