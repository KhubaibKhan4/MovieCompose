package com.codespacepro.moviecompose.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.codespacepro.moviecompose.components.TrendingList
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.repository.Repository
import com.codespacepro.moviecompose.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Popular(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current


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


    try {


        //Trending TV Data
        mainViewModel.getTopRated("en-US", 1)
        mainViewModel.myTopRatedResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                popularMovie = response.body()
                Log.d("HomeScreen", "$popularMovie")
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
                    title = { Text(text = "Most Popular") },
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
                        .padding(top = it.calculateTopPadding(), bottom = 69.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        popularMovie?.results?.let { it1 ->
                            TrendingList(
                                result = it1,
                                context = context,
                                navController
                            )
                        }
                    }
                }

            })

    }
}