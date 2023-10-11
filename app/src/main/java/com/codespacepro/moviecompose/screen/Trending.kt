package com.codespacepro.moviecompose.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.codespacepro.moviecompose.components.PersonProfileList
import com.codespacepro.moviecompose.components.TrendingTVList
import com.codespacepro.moviecompose.components.TrendingWeeklyList
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.person.Person
import com.codespacepro.moviecompose.model.tv.Tv
import com.codespacepro.moviecompose.repository.Repository
import com.codespacepro.moviecompose.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Trending(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var trendingToday by remember {
        mutableStateOf<Tv?>(null)
    }
    var trendingWeekly by remember {
        mutableStateOf<Movies?>(null)
    }

    var trendingTvToday by remember {
        mutableStateOf<Tv?>(null)
    }
    var popularMovie by remember {
        mutableStateOf<Movies?>(null)
    }
    var query by remember {
        mutableStateOf("")
    }
    var isActive by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    var personData by remember {
        mutableStateOf<Person?>(null)
    }

    try {
        //Trending Today
        mainViewModel.getTrendingTVToday("en-US")
        mainViewModel.myTrendingTVTodayResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trendingToday = response.body()
                Log.d("TrendingScreen", "$trendingToday")
                isLoading = false
            } else {
                isLoading = false
                Log.d("TrendingScreen", "${response.code()}")
            }
        })

        //Trending Movie Response
        mainViewModel.getTrendingWeekly("en-US")
        mainViewModel.myTrendingWeeklyResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trendingWeekly = response.body()
                Log.d("TrendingScreen", "$trendingWeekly")
                isLoading = false
            } else {
                isLoading = false
                Log.d("TrendingScreen", "${response.code()}")
            }
        })

        //Actors Data
        mainViewModel.getTrendingPersonToday("en-US")
        mainViewModel.myTrendingPersonTodayResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                personData = response.body()
                Log.d("HomeScreen", "$personData")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        //Trending TV Data
        mainViewModel.getTrendingTVToday("en-US")
        mainViewModel.myTrendingTVTodayResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trendingTvToday = response.body()
                Log.d("HomeScreen", "$trendingTvToday")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })


    } catch (e: Exception) {
        Log.d("TV", "Trending: ${e.printStackTrace()}")
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Trending Now") },
                    actions = {
                        IconButton(
                            onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Refresh, contentDescription = "",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            content = {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        trendingWeekly?.results?.let { TrendingWeeklyList(result = it, navController) }
                        Spacer(modifier = Modifier.height(4.dp))
                        personData?.results?.let { it1 ->
                            PersonProfileList(
                                person = it1,
                                topBarText = "Trending Actors",
                                context = context,
                                navController
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        trendingTvToday?.results?.let { it1 ->
                            TrendingTVList(
                                result = it1,
                                topBarText = "TV Series",
                                navController
                            )
                        }
//                        Spacer(modifier = Modifier.height(8.dp))
//                        trendingToday?.results?.let { result ->
//                            TrendingList(
//                                result = result,
//                                context = context
//                            )
//                        }
                        Spacer(modifier = Modifier.height(69.dp))

                    }
                }

            })

    }
}