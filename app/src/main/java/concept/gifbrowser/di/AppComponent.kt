package concept.gifbrowser.di

import concept.gifbrowser.ui.grid.GridViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = [AppModule::class, UtilsModule::class, ApiModule::class])
interface AppComponent {

    fun inject(gridViewModel: GridViewModel)

}