package com.arya.moviecatalogue.ui.favorite.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.ViewModelFactory
import com.arya.moviecatalogue.databinding.FragmentTvShowBinding
import com.arya.moviecatalogue.databinding.FragmentTvShowFavoriteBinding
import com.arya.moviecatalogue.ui.tvshow.TvShowAdapter
import com.arya.moviecatalogue.ui.tvshow.TvShowViewModel
import com.arya.moviecatalogue.vo.Status
import com.google.android.material.snackbar.Snackbar


class FavoriteTvShowFragment : Fragment() {

    private lateinit var binding: FragmentTvShowFavoriteBinding
    private lateinit var viewModel: TvShowViewModel
    private lateinit var favAdapter: TvShowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvTvShowsFavorite)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            favAdapter = TvShowAdapter()

            viewModel.getTvShowsFavorite().observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    favAdapter.submitList(tvShows)
                }
            })


            with(binding.rvTvShowsFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = favAdapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = favAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { viewModel.setFavorite(it) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    courseEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })
}