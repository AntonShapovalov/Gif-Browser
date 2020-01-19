package concept.gifbrowser.ui.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import concept.gifbrowser.R
import concept.gifbrowser.data.ImageItem

/**
 * List adapter for [GridFragment]
 */
class GridAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private val items: ArrayList<ImageItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        val holder = ViewHolder(v)
        v.setOnClickListener {
            val position = holder.adapterPosition
            if (position >= 0) onItemClick(items[position].originalUrl)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val view = holder.itemView
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<ImageItem>) {
        items.clear()
        items.addAll(localList())
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // TODO: testing
    private fun localList(): List<ImageItem> {
        val items = ArrayList<ImageItem>()
        (0..24).forEach { i ->
            val item = ImageItem("p$i", "o$i")
            items.add(item)
        }
        return items
    }

}