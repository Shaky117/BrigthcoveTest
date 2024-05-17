package com.zhaaky.brightcovetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaaky.brightcovetest.models.TvShow
import com.zhaaky.brightcovetest.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.util.Calendar

class TVShowViewModel : ViewModel() {

    private val tvMazeService = RetrofitInstance.retrofitService

    private val _shows = MutableLiveData<List<TvShow>>()
    val shows : LiveData<List<TvShow>> get() = _shows

    fun fetchShows(){
        viewModelScope.launch{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val monthFormated = String.format("%02d", month)
            val dayFormated = String.format("%02d", day)

            val date = "$year-$monthFormated-$dayFormated"
            _shows.value = tvMazeService.getSchedule(date)
        }
    }
}