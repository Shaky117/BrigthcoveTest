package com.zhaaky.brightcovetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhaaky.brightcovetest.ui.TVShowViewModel

class MainActivity : AppCompatActivity() {


    lateinit var tvViewModel : TVShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvViewModel = ViewModelProvider(this).get(TVShowViewModel::class.java)

        addObservers()
        fetchData()
    }

    private fun fetchData() {
        tvViewModel.fetchShows()
    }

    private fun addObservers() {
        tvViewModel.shows.observe(this, Observer {
            updateViews()
        })
    }

    private fun updateViews() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
    }
}