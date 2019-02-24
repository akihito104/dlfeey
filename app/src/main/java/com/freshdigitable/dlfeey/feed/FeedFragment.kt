package com.freshdigitable.dlfeey.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshdigitable.dlfeey.R
import com.freshdigitable.dlfeey.databinding.ViewFeedItemBinding
import com.rometools.rome.feed.synd.SyndEntry
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FeedFragment : Fragment() {
    @Inject
    lateinit var factory : ViewModelProvider.Factory
    @Inject
    lateinit var recycledViewPool: RecyclerView.RecycledViewPool

    companion object {
        private const val ARGS_FEED_URL = "feed_url"

        fun newInstance(url: String): FeedFragment {
            val args = Bundle().apply {
                putString(ARGS_FEED_URL, url)
            }
            return FeedFragment().apply {
                arguments = args
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this, factory).get(FeedViewModel::class.java)

        val listView = view.findViewById<RecyclerView>(R.id.feed_list) ?: throw IllegalStateException()
        listView.layoutManager = LinearLayoutManager(view.context)
        listView.setHasFixedSize(true)
        listView.setRecycledViewPool(recycledViewPool)

        val adapter = Adapter(viewModel)
        listView.adapter = adapter

        viewModel.feed.observe(viewLifecycleOwner, Observer{ f ->
            f?.let {
                adapter.items.clear()
                adapter.items.addAll(it.entries)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.loadFeed(url)
    }

    private val url
        get() = arguments?.getString(ARGS_FEED_URL) ?: throw IllegalArgumentException("use newInstance()")
}

class Adapter(
    private val viewModel: FeedViewModel
) : RecyclerView.Adapter<ViewHolder>() {
    val items = mutableListOf<SyndEntry>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = R.layout.view_feed_item

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ViewFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
        holder.binding.viewModel = viewModel
    }
}

class ViewHolder(
    val binding: com.freshdigitable.dlfeey.databinding.ViewFeedItemBinding
) : RecyclerView.ViewHolder(binding.root)
