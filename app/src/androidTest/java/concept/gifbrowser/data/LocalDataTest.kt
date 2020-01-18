package concept.gifbrowser.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import concept.gifbrowser.di.AppModule
import concept.gifbrowser.di.DaggerAppTestComponent
import concept.gifbrowser.di.UtilsModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Test for [LocalData]
 */
@RunWith(AndroidJUnit4::class)
class LocalDataTest {

    @Inject lateinit var localData: LocalData

    private val testImages: List<ImageItem> = listOf(
        ImageItem("p1", "o2"),
        ImageItem("p2", "o2")
    )

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        DaggerAppTestComponent.builder()
            .utilsModule(UtilsModule())
            .appModule(AppModule(context))
            .build()
            .inject(this)
        localData.clear()
    }

    @Test
    fun saveRemoteData() {
        Assert.assertTrue(localData.isCacheExpired())
        localData.saveImages(testImages)
        Assert.assertFalse(localData.isCacheExpired())
        val savedArray = localData.getImages().toTypedArray()
        val testArray = testImages.toTypedArray()
        Assert.assertTrue(savedArray.contentEquals(testArray))
    }

    @After
    fun tearDown() = localData.clear()

}