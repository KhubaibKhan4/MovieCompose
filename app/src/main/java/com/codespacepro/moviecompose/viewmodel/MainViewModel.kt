package com.codespacepro.moviecompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.person.Person
import com.codespacepro.moviecompose.model.tv.Tv
import com.codespacepro.moviecompose.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _myResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTopRatedResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myUpComingResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTrendingTodayResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTrendingWeeklyResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTrendingPersonWeeklyResponse: MutableLiveData<Response<Person>> =
        MutableLiveData()
    private val _myTrendingPersonTodayResponse: MutableLiveData<Response<Person>> =
        MutableLiveData()
    private val _myTrendingTVTodayResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTrendingTVWeeklyResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTrendingMovieResponse: MutableLiveData<Response<Movies>> = MutableLiveData()
    private val _myTopRatedTv: MutableLiveData<Response<Tv>> = MutableLiveData()

    val myResponse: LiveData<Response<Movies>> = _myResponse
    val myTopRatedResponse: LiveData<Response<Movies>> = _myTopRatedResponse
    val myUpComingResponse: LiveData<Response<Movies>> = _myUpComingResponse
    val myTrendingTodayResponse: LiveData<Response<Movies>> = _myTrendingTodayResponse
    val myTrendingWeeklyResponse: LiveData<Response<Movies>> = _myTrendingWeeklyResponse
    val myTrendingPersonWeeklyResponse: LiveData<Response<Person>> = _myTrendingPersonWeeklyResponse
    val myTrendingPersonTodayResponse: LiveData<Response<Person>> = _myTrendingPersonTodayResponse
    val myTrendingTVTodayResponse: LiveData<Response<Movies>> = _myTrendingTVTodayResponse
    val myTrendingTVWeeklyResponse: LiveData<Response<Movies>> = _myTrendingTVWeeklyResponse
    val myTrendingMovieResponse: LiveData<Response<Movies>> = _myTrendingMovieResponse
    val myTopRatedTv: LiveData<Response<Tv>> = _myTopRatedTv


    fun getPopular(lang: String, page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPopular(lang, page)
                _myResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTopRated(lang: String, page: Int) {
        viewModelScope.launch {

            try {
                val response = repository.getTopRated(lang, page)
                _myTopRatedResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getUpComing(lang: String, page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTopRated(lang, page)
                _myUpComingResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingToday(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingToday(lang)
                _myTrendingTodayResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingWeekly(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingWeekly(lang)
                _myTrendingWeeklyResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingPersonWeekly(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingPersonWeekly(lang)
                _myTrendingPersonWeeklyResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingPersonToday(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingPersonToday(lang)
                _myTrendingPersonTodayResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingTVToday(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingTVToday(lang)
                _myTrendingTVTodayResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingTVWeekly(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingTVWeekly(lang)
                _myTrendingTVWeeklyResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTrendingMovie(lang: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingMovie(lang)
                _myTrendingMovieResponse.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

    fun getTvTopRatedSeries(lang: String, page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTvTopRatedSeries(lang,page)
                _myTopRatedTv.value = response
            } catch (e: Exception) {
                Log.d("Home", "${e.printStackTrace()}")
            }

        }
    }

}