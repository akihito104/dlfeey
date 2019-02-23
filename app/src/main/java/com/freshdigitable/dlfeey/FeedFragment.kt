package com.freshdigitable.dlfeey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshdigitable.dlfeey.databinding.ViewFeedItemBinding
import com.rometools.rome.feed.synd.SyndEntry

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        val listView = view.findViewById<RecyclerView>(R.id.feed_list) ?: throw IllegalStateException()
        listView.layoutManager = LinearLayoutManager(view.context)
        listView.setHasFixedSize(true)

        val adapter = Adapter()
        listView.adapter = adapter

        viewModel.feed.observe(viewLifecycleOwner, Observer{ f ->
            adapter.items.clear()
            adapter.items.addAll(f.entries)
            adapter.notifyDataSetChanged()
        })

        viewModel.loadFeed("http://b.hatena.ne.jp/hotentry.rss")
    }
}

class Adapter : RecyclerView.Adapter<ViewHolder>() {
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
        return ViewHolder(ViewFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
    }
}

class ViewHolder(
    val binding: com.freshdigitable.dlfeey.databinding.ViewFeedItemBinding
) : RecyclerView.ViewHolder(binding.root)
