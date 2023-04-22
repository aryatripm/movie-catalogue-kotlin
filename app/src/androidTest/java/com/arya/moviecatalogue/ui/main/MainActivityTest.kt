package com.arya.moviecatalogue.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.utils.DataDummy
import com.arya.moviecatalogue.utils.EspressoIdlingResource
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        }
    }

    @Test
    fun loadMovie() {
        onView(withText("Movies")).perform(click())
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        }


        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.title_detail)).check(matches(withText(DataDummy.getMovies()[0].title)))

        onView(withId(R.id.overview_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_detail)).check(matches(withText(DataDummy.getMovies()[0].overview)))

        onView(withId(R.id.release_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_detail)).check(matches(withText(DataDummy.getMovies()[0].release_date)))

        onView(withId(R.id.vote_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.vote_detail)).check(matches(withText(DataDummy.getMovies()[0].vote_average.toString())))

        onView(withId(R.id.poster_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))


        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.title_detail)).check(matches(withText(DataDummy.getTvShows()[0].original_name)))

        onView(withId(R.id.overview_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_detail)).check(matches(withText(DataDummy.getTvShows()[0].overview)))

        onView(withId(R.id.release_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_detail)).check(matches(withText(DataDummy.getTvShows()[0].first_air_date)))

        onView(withId(R.id.vote_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.vote_detail)).check(matches(withText(DataDummy.getTvShows()[0].vote_average.toString())))

        onView(withId(R.id.poster_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteMovies() {
        // tampil movies, klik favorite, back
        onView(withText("Movies")).perform(click())
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        }

        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())

        // klik favorite, tampil movie, cek
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.rv_movies_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.title_detail)).check(matches(withText(DataDummy.getMovies()[0].title)))

        onView(withId(R.id.overview_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_detail)).check(matches(withText(DataDummy.getMovies()[0].overview)))

        onView(withId(R.id.release_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_detail)).check(matches(withText(DataDummy.getMovies()[0].release_date)))

        onView(withId(R.id.vote_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.vote_detail)).check(matches(withText(DataDummy.getMovies()[0].vote_average.toString())))

        onView(withId(R.id.poster_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShows() {
        // tampil tvshow, klik favorite, back
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())

        // klik favorite, tampil tv show, cek
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())

        onView(withId(R.id.rv_tv_shows_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.title_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.title_detail)).check(matches(withText(DataDummy.getTvShows()[0].original_name)))

        onView(withId(R.id.overview_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_detail)).check(matches(withText(DataDummy.getTvShows()[0].overview)))

        onView(withId(R.id.release_date_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date_detail)).check(matches(withText(DataDummy.getTvShows()[0].first_air_date)))

        onView(withId(R.id.vote_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.vote_detail)).check(matches(withText(DataDummy.getTvShows()[0].vote_average.toString())))

        onView(withId(R.id.poster_detail)).check(matches(isDisplayed()))
    }
}