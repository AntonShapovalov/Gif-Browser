package concept.gifbrowser.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import concept.gifbrowser.R
import concept.gifbrowser.ui.activity.getGridSpanCount
import kotlinx.android.synthetic.main.fragment_grid.*

class GridFragment : Fragment() {

    private lateinit var viewModel: GridViewModel
    private val adapter = GridAdapter { showFullScreen(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, getGridSpanCount())
        layoutManager.isSmoothScrollbarEnabled = true
        imagesGrid.layoutManager = layoutManager
        imagesGrid.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get(GridViewModel::class.java)
        adapter.setItems(emptyList()) //  TODO: test
    }

    private fun showFullScreen(originalUrl: String) {
        val direction = GridFragmentDirections.actionOpenFullScreen(originalUrl)
        findNavController().navigate(direction)
    }

}