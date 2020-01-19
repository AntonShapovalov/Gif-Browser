package concept.gifbrowser.ui.activity

import android.content.res.Resources
import android.graphics.Rect
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import concept.gifbrowser.R
import concept.gifbrowser.app.GBApplication
import concept.gifbrowser.di.AppComponent

val FragmentActivity.appComponent: AppComponent get() = (application as GBApplication).appComponent

fun Resources.getGridSpanCount(): Int {
    val imageSize = getDimensionPixelSize(R.dimen.image_size)
    return displayMetrics.widthPixels / (imageSize + 2 * getGridItemOffset())
}

fun Resources.getGridItemOffset(): Int = getDimensionPixelOffset(R.dimen.image_offset)

class GridItemDecorator(resources: Resources) : RecyclerView.ItemDecoration() {
    private val offset = resources.getGridItemOffset()
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.set(offset, offset, offset, offset)
    }
}
