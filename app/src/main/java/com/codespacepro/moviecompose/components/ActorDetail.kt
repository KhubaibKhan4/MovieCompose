package com.codespacepro.moviecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetail(
    navController: NavHostController,
    profile_path: String?,
    name: String?,
    known_for_department: String?,
    gender: String?,
    popularity: String?
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
                    listOf(known_for_department)
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
    knownFor: List<String?> // List of known movies/TV shows
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Actor's profile image
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
        ) {
            if (profile_path?.isNotEmpty() == true) {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data("https://image.tmdb.org/t/p/w500${profile_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(75.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder image or text if the profile image is not available
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.Gray),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = name ?: "Unknown",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actor's name
        Text(
            text = name ?: "Unknown",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Actor's known for department
        Text(
            text = "Department: ${known_for_department ?: "N/A"}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Actor's gender
        Text(
            text = "Gender: ${gender ?: "N/A"}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Actor's popularity
        Text(
            text = "Popularity: ${popularity ?: "N/A"}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Actor's known for movies/TV shows
        Text(
            text = "Known For:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // List of known movies/TV shows
        Column {
            knownFor.forEach { knownForItem ->
                if (knownForItem != null) {
                    Text(
                        text = knownForItem,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

