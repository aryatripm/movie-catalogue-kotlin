package com.arya.moviecatalogue.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.databinding.ActivityFavoriteBinding
import com.arya.moviecatalogue.ui.main.SectionsPagerAdapter

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.favorite)

        val sectionsPagerAdapter = FavoriteSectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPagerFavorite.adapter = sectionsPagerAdapter
        binding.tabsFavorite.setupWithViewPager(binding.viewPagerFavorite)

        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}