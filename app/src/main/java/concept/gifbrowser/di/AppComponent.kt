package concept.gifbrowser.di

import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = [AppModule::class, UtilsModule::class, ApiModule::class])
interface AppComponent