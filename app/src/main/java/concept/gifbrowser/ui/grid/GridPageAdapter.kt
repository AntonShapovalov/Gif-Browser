package concept.gifbrowser.ui.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import concept.gifbrowser.R
import concept.gifbrowser.data.ImageItem
import timber.log.Timber

/**
 * List adapter for [GridFragment]
 */
class GridPageAdapter(
    private val fragment: GridFragment,
    private val onItemClick: (String) -> Unit
) : PagedListAdapter<ImageItem, GridPageAdapter.ViewHolder>(
    GIFS_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        val holder = ViewHolder(v)
        v.setOnClickListener {
            val position = holder.adapterPosition
            if (position >= 0) getItem(position)?.originalUrl?.let {
                onItemClick(it)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        Timber.d("id = %s", item.id)
        Glide.with(fragment)
            .load(item.previewUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.itemView as ImageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        val GIFS_COMPARATOR = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}