package com.arya.moviecatalogue.ui.favorite.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.ViewModelFactory
import com.arya.moviecatalogue.databinding.FragmentMovieBinding
import com.arya.moviecatalogue.databinding.FragmentMovieFavoriteBinding
import com.arya.moviecatalogue.ui.movie.MovieAdapter
import com.arya.moviecatalogue.ui.movie.MovieViewModel
import com.google.android.material.snackbar.Snackbar


class FavoriteMovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieFavoriteBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var favAdapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvMoviesFavorite)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        favAdapter = MovieAdapter()

        viewModel.getMoviesFavorite().observe(requireActivity(), { movies ->
            favAdapter.submitList(movies)
        })

        with(binding.rvMoviesFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favAdapter
        }

    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = favAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavorite(it) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    movieEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })
}