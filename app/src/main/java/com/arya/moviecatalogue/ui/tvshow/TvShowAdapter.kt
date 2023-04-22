package com.arya.moviecatalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.databinding.RvItemBinding
import com.arya.moviecatalogue.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    /*private var tvShows = ArrayList<TvShowEntity>()

    fun setTvShows(tvShows: List<TvShowEntity>?) {
        if (tvShows == null) return
        this.tvShows.clear()
        this.tvShows.addAll(tvShows)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowAdapter.ViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowAdapter.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    class ViewHolder(private val binding: RvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                itemTitle.text = tvShow.original_name
                itemOverview.text = tvShow.overview
                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra("TV_SHOW", tvShow.id)
                    intent.putExtra("TYPE_ID", 2)
                    it.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + tvShow.poster_path)
                    .centerCrop()
                    .into(imgPoster)
            }
        }
    }
}