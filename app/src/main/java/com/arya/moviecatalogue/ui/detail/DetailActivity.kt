package com.arya.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.ViewModelFactory
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.databinding.ActivityDetailBinding
import com.arya.moviecatalogue.ui.movie.MovieViewModel
import com.arya.moviecatalogue.ui.tvshow.TvShowViewModel
import com.arya.moviecatalogue.utils.Constant
import com.arya.moviecatalogue.vo.Status
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var tvShowViewModel: TvShowViewModel

    private var isFavorite: Boolean = false
    private var isMovie: Boolean = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        if (intent.getIntExtra("TYPE_ID", 0) == 1){
            isMovie = true
            val movieId = intent.getIntExtra("MOVIE", 0)
            movieViewModel.setSelectedMovie(movieId)
            movieViewModel.getMovie(movieId).observe(this, {
                it.data?.let { it1 -> setMovie(it1) }
            })


        } else if (intent.getIntExtra("TYPE_ID", 0) == 2) {
            isMovie = false
            val tvShowId = intent.getIntExtra("TV_SHOW", 0)
            tvShowViewModel.setSelectedTvShow(tvShowId)
            tvShowViewModel.getTvShow(tvShowId).observe(this, {
                it.data?.let { it1 -> setTvShow(it1) }
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        if (isMovie) {
            movieViewModel.movieDetail.observe(this, { movie->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> Toast.makeText(this, "Loading!", Toast.LENGTH_SHORT).show()
                        Status.ERROR -> Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                        Status.SUCCESS -> {
                            if (movie.data != null) {
                                val state = movie.data.isFavorite
                                setFavorite(state)
                            }
                        }
                    }
                }
            })
        } else {
            tvShowViewModel.tvShowDetail.observe(this, { tvShow ->
                if (tvShow != null) {
                    when (tvShow.status) {
                        Status.SUCCESS -> {
                            if (tvShow.data != null) {
                                val state = tvShow.data.isFavorite
                                setFavorite(state)
                            }
                        }
                        Status.ERROR -> Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                        Status.LOADING -> Toast.makeText(this, "Loading!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            if (isMovie) {
                movieViewModel.setMovieFavorite()
            } else {
                tvShowViewModel.setTvShowFavorite()
            }
            setFavorite(!isFavorite)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setFavorite(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_outline_24)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setMovie(movie: MovieEntity) {

        with(binding) {
            titleDetail.text = movie.title
            /*taglineDetail.text = movie.tagline
            runtimeDetail.text = movie.runtime.toString()*/
            voteDetail.text = movie.vote_average.toString()
            releaseDateDetail.text = movie.release_date
            // genresDetail.text = movie.genres.toString()
            overviewDetail.text = movie.overview
            Glide.with(baseContext)
                .load(Constant.IMAGE_URL + movie.poster_path)
                .into(posterDetail)
        }
    }

    private fun setTvShow(tvShow: TvShowEntity) {
        with(binding) {
            Glide.with(baseContext)
                .load(Constant.IMAGE_URL + tvShow.poster_path)
                .into(posterDetail)
            titleDetail.text = tvShow.original_name
            // taglineDetail.text = tvShow.tagline
            // runtimeDetail.text = tvShow.episode_run_time[0].toString()
            voteDetail.text = tvShow.vote_average.toString()
            releaseDateDetail.text = tvShow.first_air_date
            // genresDetail.text = tvShow.genres.toString()
            overviewDetail.text = tvShow.overview
        }
    }
}