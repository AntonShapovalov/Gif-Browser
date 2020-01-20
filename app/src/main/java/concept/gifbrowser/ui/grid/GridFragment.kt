package concept.gifbrowser.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import concept.gifbrowser.R
import concept.gifbrowser.ui.activity.*
import concept.githubfavoriterepo.ui.activity.StateError
import concept.githubfavoriterepo.ui.activity.StateProgress
import concept.githubfavoriterepo.ui.activity.ViewModelState
import concept.githubfavoriterepo.ui.activity.name
import kotlinx.android.synthetic.main.fragment_grid.*
import timber.log.Timber

class GridFragment : Fragment() {

    private lateinit var viewModel: GridViewModel
    private lateinit var adapter: GridPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, resources.getGridSpanCount())
        layoutManager.isSmoothScrollbarEnabled = true
        imagesGrid.layoutManager = layoutManager
        imagesGrid.addItemDecoration(GridItemDecorator(resources))
        adapter = GridPageAdapter(this) { showFullScreen(it) }
        imagesGrid.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val act = activity ?: return
        viewModel = ViewModelProviders.of(this)
            .get(GridViewModel::class.java)
            .also { act.appComponent.inject(it) }
            .also { vm -> vm.state.observe(this, Observer { onStateChanged(it) }) }
            .also { vm -> vm.getImages()?.observe(this, Observer { adapter.submitList(it) }) }
    }

    private fun onStateChanged(state: ViewModelState?) {
        Timber.d(state?.name())
        updateProgress(state is StateProgress)
        if (state is StateError) showError(state.throwable)
    }

    private fun updateProgress(isProcess: Boolean) {
        if (isProcess) progress.show() else progress.gone()
    }

    private fun showError(throwable: Throwable) {
        Timber.e(throwable)
        val text = throwable.localizedMessage
        Snackbar.make(gridFrame, text ?: "Unknown error", Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.text_retry) { viewModel.retry() }
            .show()
    }

    private fun showFullScreen(originalUrl: String) {
        val direction = GridFragmentDirections.actionOpenFullScreen(originalUrl)
        findNavController().navigate(direction)
    }

}