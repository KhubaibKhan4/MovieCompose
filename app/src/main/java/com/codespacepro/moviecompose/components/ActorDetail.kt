package com.codespacepro.moviecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetail(
    navController: NavHostController,
    profile_path: String?,
    name: String?,
    known_for_department: String?,
    gender: String?,
    popularity: String?,
    know_for: String?,
    backdrop_path: String?,
    overview: String?,
    release_date: String?,
    vote_count: String?
) {
    var isLiked by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    // Movie title and top app bar
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(title = {
                Text(
                    text = "${name ?: "No Name Found..."}",
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
                            .padding(end = 15.dp)
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
                ActorDetailItem(
                    profile_path = profile_path,
                    name = name,
                    known_for_department = known_for_department,
                    gender = gender,
                    popularity = popularity,
                    know_for,
                    backdrop_path,
                    overview,
                    release_date,
                    vote_count
                )
            }

        })
}

@Composable
fun ActorDetailItem(
    profile_path: String?,
    name: String?,
    known_for_department: String?,
    gender: String?,
    popularity: String?,
    know_for: String?,
    backdrop_path: String?,
    overview: String?,
    release_date: String?,
    vote_count: String?,
) {
    val context = LocalContext.current
    val uri = "https://image.tmdb.org/t/p/w500$profile_path"
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Actor Profile Image

            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${profile_path}")
                    .crossfade(enable = true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )


        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actor Name
        Text(
            text = "Original Name: $name",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Actor Gender
        Text(
            text = "Gender: ${if (gender!!.toInt() == 2) "Male" else "Female"}",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Actor Known For
        Text(
            text = "Known For: ${known_for_department}",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data("https://image.tmdb.org/t/p/w500${backdrop_path}")
                .crossfade(enable = true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(8.dp))
// Actor Popularity
        Text(
            text = "Movie Name: ${know_for}",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Actor Popularity
        Text(
            text = "Popularity: ${popularity}",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
            )
        )



        Spacer(modifier = Modifier.height(8.dp))

        // Additional Details
        Text(
            text = "Overview: $overview",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Release Date: $release_date",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Vote Count: $vote_count",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
            )
        )


        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            onClick = {
                uriHandler.openUri(uri)
            },
            modifier = Modifier
                .size(30.dp)
                .clip(shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.open_in_new), contentDescription = "",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 72.dp))

    }
}

