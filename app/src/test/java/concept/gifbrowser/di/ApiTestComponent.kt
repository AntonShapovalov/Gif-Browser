package concept.gifbrowser.di

import concept.gifbrowser.data.RemoteDataTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provide dependencies for [RemoteDataTest]
 */
@Singleton
@Component(modules = [UtilsModule::class, ApiModule::class])
interface ApiTestComponent {

    fun inject(test: RemoteDataTest)

}