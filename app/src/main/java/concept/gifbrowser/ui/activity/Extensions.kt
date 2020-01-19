package concept.gifbrowser.ui.activity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import concept.gifbrowser.R
import concept.gifbrowser.app.GBApplication
import concept.gifbrowser.di.AppComponent

val FragmentActivity.appComponent: AppComponent get() = (application as GBApplication).appComponent

fun View.show() = let { visibility = View.VISIBLE }

fun View.gone() = let { visibility = View.GONE }

fun View.showOrHide(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun Fragment.getGridSpanCount(): Int {
    val imageSize = resources.getDimensionPixelSize(R.dimen.image_size)
    val imageMargin = resources.getDimensionPixelOffset(R.dimen.image_margin)
    return resources.displayMetrics.widthPixels / (imageSize + 2 * imageMargin)

}
