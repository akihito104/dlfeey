package com.freshdigitable.dlfeey.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.freshdigitable.dlfeey.databinding.FragmentFeedDetailBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FeedDetailFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: FragmentFeedDetailBinding

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this, factory).get(FeedDetailViewModel::class.java)
        val item = viewModel.getItem(uri)
        binding.item = item
    }

    companion object {
        fun newInstance(uri: String): FeedDetailFragment {
            val args = Bundle().apply {
                putString("uri", uri)
            }
            return FeedDetailFragment().apply {
                arguments = args
            }
        }
    }

    private val uri: String
        get() = arguments?.getString("uri") ?: throw IllegalArgumentException("use newInstance()")
}
