package com.codespacepro.moviecompose.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.R
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.Result
import com.codespacepro.moviecompose.repository.Repository
import com.codespacepro.moviecompose.viewmodel.MainViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetail(
    navController: NavHostController,
    postImage: String?,
    title: String?,
    overview: String?,
    pubDate: String?,
    voteAverage: String?,
    id: String?
) {
    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var similar by remember {
        mutableStateOf<Movies?>(null)
    }
    var recommendation by remember {
        mutableStateOf<Movies?>(null)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    var isLiked by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    try {
        id?.toInt()?.let { mainViewModel.getSimilar("en-US", 1, it) }
        mainViewModel.mySimilar.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                similar = response.body()
                Log.d("HomeScreen", "$similar")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        mainViewModel.getRecommendation("en-US", 1, movieId = id!!.toInt())
        mainViewModel.myRecommendations.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                recommendation = response.body()
                Log.d("HomeScreen", "$recommendation")
                isLoading = false
            } else {
                isLoading = false
                Log.d("HomeScreen", "${response.code()}")
            }
        })

    } catch (e: Exception) {
        Log.d("Exception", "HomeScreen: ${e.printStackTrace()}")
    }
    // Movie title and top app bar
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(title = {
                Text(
                    text = "${title ?: "No Title Found..."}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.12.sp
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isLiked = !isLiked },
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                            .background(
                                color = Color(0xFF252836),
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            tint = if (isLiked) Color.Red else Color.White
                        )
                    }
                })
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp)
            ) {
                MovieDetailsItem(
                    navController = navController,
                    postImage = postImage,
                    title = title,
                    overview = overview,
                    pubDate = pubDate,
                    voteAverage = voteAverage,
                    id = id,
                    context = context
                )
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {

                    }
                } else {
                    similar?.results?.let { it1 -> SimilarMovies(result = it1) }
                    Spacer(modifier = Modifier.height(8.dp))
                    recommendation?.results?.let { it1 -> RecommendedMovies(result = it1) }
                    Spacer(modifier = Modifier.padding(bottom = 72.dp))
                }
            }

        })
}


@Composable
fun MovieDetailsItem(
    navController: NavHostController,
    postImage: String?,
    title: String?,
    overview: String?,
    pubDate: String?,
    voteAverage: String?,
    id: String?,
    context: Context
) {
    // Define background image with reduced opacity
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Black background overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Adjust opacity as needed
        )
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data("https://image.tmdb.org/t/p/w500${postImage}")
                .crossfade(enable = true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
            colorFilter = ColorFilter.tint(Color.Black, blendMode = BlendMode.Overlay)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            // Movie poster
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${postImage}")
                    .crossfade(enable = true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(287.dp)

                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            // Movie details (Release date, duration, genre)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${pubDate?.let { convertDate(it) }}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92929D),
                    letterSpacing = 0.12.sp,
                )
                LineSpacer()
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.watch), // Use your drawable resource here
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "148 Minutes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92929D),
                    letterSpacing = 0.12.sp,
                )
                LineSpacer()
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    painter = painterResource(id = R.drawable.movie), // Use your drawable resource here
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Action",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92929D),
                    letterSpacing = 0.12.sp,
                )
            }

            // Rating and buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Top, // Display rating above buttons
                horizontalAlignment = Alignment.CenterHorizontally // Center align the content
            ) {
                // Rating
                MovieRating(rating = voteAverage.toString())

                // Play button and action buttons
                postImage?.let { MovieActionButtons(uriUrl = it) }

            }

            // Storyline
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${title ?: "No Title Found..."}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color.White,
                    letterSpacing = 0.12.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$overview",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEBEBEF),
                    letterSpacing = 0.12.sp,
                )
            }
        }


    }


}

@Composable
fun MovieDetailInfo(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF92929D),
            letterSpacing = 0.12.sp,
        )
    }
}

@Composable
fun MovieRating(rating: String) {
    Card(
        modifier = Modifier
            .width(85.dp)
            .height(44.dp)
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
            .background(
                color = Color(0x52252836),
                shape = RoundedCornerShape(size = 8.dp)
            )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0XFFFF8700)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$rating",
                fontSize = 12.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFFF8700),
                letterSpacing = 0.12.sp
            )
        }
    }
}

@Composable
fun MovieActionButtons(uriUrl: String) {
    val uri = "https://image.tmdb.org/t/p/w500$uriUrl"
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Play button
        PlayButton(postImage =uriUrl)

        Spacer(modifier = Modifier.width(8.dp))
        // Download button
        MovieIconButton(
            icon = painterResource(id = R.drawable.download),
            onClick = {
                uriHandler.openUri(uri)
            },
            backgroundColor = Color(0xFF252836),
            iconTint = Color(0XFFFF8700)
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Open in new button
        MovieIconButton(
            icon = painterResource(id = R.drawable.open_in_new),
            onClick = {
                uriHandler.openUri(uri)
            },
            backgroundColor = Color(0xFF252836),
            iconTint = Color(0XFF12CDD9)
        )
    }
}

@Composable
fun PlayButton(postImage: String?) {
    val uri = "https://image.tmdb.org/t/p/w500$postImage"
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .width(115.dp)
            .height(48.dp)
            .clickable {
                uriHandler.openUri(uri)
            }
            .background(
                color = Color(0xFFFF8700),
                shape = RoundedCornerShape(size = 32.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Play", color = Color.White)
        }
    }
}

@Composable
fun MovieIconButton(
    icon: Painter,
    onClick: () -> Unit,
    backgroundColor: Color,
    iconTint: Color
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun LineSpacer() {
    Spacer(modifier = Modifier.width(8.dp))
    Text(
        text = "|",
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFF696974))
            .padding(1.dp)
            .width(0.dp)
            .height(16.dp)
    )
}

@Composable
fun SimilarMovies(result: List<Result>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Similar",

            // H4/Semibold
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.12.sp,
            ),
            fontWeight = FontWeight(600),
        )
        Text(
            text = "See All",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF12CDD9),
                letterSpacing = 0.12.sp,
            ),
            fontWeight = FontWeight(500),
        )
    }
    LazyRow {
        items(result) { result ->
            SimilarMoviesItem(result = result)
        }
    }
}

@Composable
fun RecommendedMovies(result: List<Result>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Recommended",

            // H4/Semibold
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.12.sp,
            ),
            fontWeight = FontWeight(600),
        )
        Text(
            text = "See All",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF12CDD9),
                letterSpacing = 0.12.sp,
            ),
            fontWeight = FontWeight(500),
        )
    }
    LazyRow {
        items(result) { result ->
            RecommendedMoviesItem(result = result)
        }
    }
}

@Composable
fun RecommendedMoviesItem(result: Result) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .width(135.dp)
                .height(231.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${result.poster_path}")
                    .crossfade(enable = true)
                    .build(), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(color = Color.Black)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "${result.title}",
                    fontSize = 14.sp,
                    color = Color(0xFFFFFFFF),
                    letterSpacing = 0.12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Action",
                    fontSize = 10.sp,
                    color = Color.White,
                    letterSpacing = 0.12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }

}


@Composable
fun SimilarMoviesItem(result: Result) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .width(135.dp)
                .height(231.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${result.poster_path}")
                    .crossfade(enable = true)
                    .build(), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(color = Color.Black)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "${result.title}",
                    fontSize = 14.sp,
                    color = Color(0xFFFFFFFF),
                    letterSpacing = 0.12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Action",
                    fontSize = 10.sp,
                    color = Color.White,
                    letterSpacing = 0.12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }

}
