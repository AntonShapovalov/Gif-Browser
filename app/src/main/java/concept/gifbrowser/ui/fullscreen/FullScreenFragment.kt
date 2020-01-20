package concept.gifbrowser.ui.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import concept.gifbrowser.R
import kotlinx.android.synthetic.main.fragment_full_screen.*

class FullScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.let { FullScreenFragmentArgs.fromBundle(it).originUrl }
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_placeholder)
            .addListener(LoadingListener(progress))
            .into(imageFullScreen)
    }

}