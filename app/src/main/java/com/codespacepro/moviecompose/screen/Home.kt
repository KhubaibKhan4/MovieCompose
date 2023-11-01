package com.codespacepro.moviecompose.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.codespacepro.moviecompose.components.PersonProfileList
import com.codespacepro.moviecompose.components.PopularMoviesList
import com.codespacepro.moviecompose.components.SearchMovieList
import com.codespacepro.moviecompose.components.TopAppBar
import com.codespacepro.moviecompose.components.TopSlider
import com.codespacepro.moviecompose.components.TrendingWeeklyList
import com.codespacepro.moviecompose.di.ktor.client.MovieApiClient
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.person.Person
import com.codespacepro.moviecompose.repository.Repository
import com.codespacepro.moviecompose.ui.theme.MovieComposeTheme
import com.codespacepro.moviecompose.viewmodel.MainViewModel
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var data by remember {
        mutableStateOf<Movies?>(null)
    }
    var trendingWeekly by remember {
        mutableStateOf<Movies?>(null)
    }
    var popularMovies by remember {
        mutableStateOf<Movies?>(null)
    }

    var personData by remember {
        mutableStateOf<Person?>(null)
    }

    var searchData by remember {
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
//    LaunchedEffect(key1 = isActive) {
//        scope.launch {
//            try {
//                val popularData = MovieApiClient.getPopular(1)
//                popularMovies = popularData
//                Log.d("HomeScreen", "Ktor PopularData $popularData ")
//
//            } catch (e: ClientRequestException) {
//                e.printStackTrace()
//            }
//        }
//    }

    try {
        mainViewModel.getPopular("en-US", 1)
        mainViewModel.myResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                data = response.body()
                Log.d("HomeScreen", "$data")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        mainViewModel.getTrendingWeekly("en-US")
        mainViewModel.myTrendingWeeklyResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trendingWeekly = response.body()
                Log.d("HomeScreen", "$trendingWeekly")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        mainViewModel.getTrendingPersonWeekly("en-US")
        mainViewModel.myTrendingPersonWeeklyResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                personData = response.body()
                Log.d("HomeScreen", "$personData")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })


        mainViewModel.getSearchMovie(query, include_adult = false, "en-US", page = 1)
        mainViewModel.mySearchedMovie.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                searchData = response.body()
                Log.d("HomeScreen", "$searchData")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })
    } catch (e: Exception) {
        Log.d("Exception", "HomeScreen: ${e.printStackTrace()}")
    }

    MovieComposeTheme(darkTheme = true) {
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
                topBar =
                {
                    TopAppBar()
                },
                content = {

                    val categories = listOf(
                        "Action",
                        "Adventure",
                        "Animation",
                        "Comedy",
                        "Crime",
                        "Documentary",
                        "Drama",
                        "Family",
                        "Fantasy",
                        "History",
                        "Horror",
                        "Music",
                        "Romance",
                        "Science Fiction",
                        "Thriller"
                    )
                    var selectedCategory by remember { mutableStateOf(categories.first()) }
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchBar(
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {
                                mainViewModel.getSearchMovie(
                                    it,
                                    include_adult = false,
                                    "en-US",
                                    page = 1
                                )
                            },
                            active = isActive,
                            onActiveChange = { isActive = !isActive },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = ""
                                )
                            },
                            placeholder = { Text(text = "Search Movie...") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = ""
                                )
                            },
                            shape = RoundedCornerShape(24.dp),
                            colors = SearchBarDefaults.colors(
                                containerColor = Color(0xFF252836),
                                dividerColor = Color.DarkGray,
                                inputFieldColors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            searchData?.results?.let { it1 ->
                                SearchMovieList(
                                    result = it1,
                                    context,
                                    navController = navController
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(14.dp))

                            data?.results?.let { result ->
                                TopSlider(
                                    result = result,
                                    context = context,
                                    scope,
                                    navController = navController
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp)
                            ) {
                                Text(
                                    text = "Categories",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = Color(0xFFFFFFFF),
                                        letterSpacing = 0.12.sp,
                                    ),
                                    fontWeight = FontWeight.ExtraBold,
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                LazyRow {
                                    item {
                                        categories.forEach { category ->
                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                ElevatedButton(
                                                    onClick = {
                                                        scope.launch(Dispatchers.IO) {
                                                            mainViewModel.getPopular("en-US", 1)
                                                        }

                                                        Log.d("main", "Categories: $category")
                                                    },
                                                    modifier = Modifier.padding(4.dp),
                                                    elevation = ButtonDefaults.buttonElevation(
                                                        defaultElevation = 6.dp,
                                                        pressedElevation = 8.dp,
                                                        hoveredElevation = 10.dp,
                                                        focusedElevation = 10.dp
                                                    ),
                                                    shape = RoundedCornerShape(12.dp),
                                                    colors = ButtonDefaults.elevatedButtonColors(
                                                        containerColor = if (selectedCategory == category) Color(
                                                            0xFF252836
                                                        )
                                                        else Color.Black
                                                    )
                                                ) {
                                                    Text(
                                                        text = category.uppercase(Locale.ROOT),
                                                        color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else Color.White
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            data?.results?.let { result ->
                                PopularMoviesList(result = result, navController)
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            trendingWeekly?.results?.let { it1 ->
                                TrendingWeeklyList(
                                    result = it1,
                                    navController
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            personData?.results?.let { it1 ->
                                PersonProfileList(
                                    person = it1,
                                    topBarText = "Top Actors",
                                    context = context,
                                    navController
                                )
                            }
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                })
        }
    }
}




