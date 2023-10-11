package com.codespacepro.moviecompose.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codespacepro.moviecompose.model.tv.ResultTv
import com.codespacepro.moviecompose.navigation.navgraph.Screen

@Composable
fun TvSeriesMovie(result: List<ResultTv>, navController: NavHostController) {
    LazyColumn {
        items(result) { result ->
            TvSeriesItem(result = result, navController)
        }
    }
}

@Composable
fun TvSeriesItem(result: ResultTv, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate(
                    Screen.MovieDetail.passData(
                        Uri.encode(result.poster_path),
                        Uri.encode(result.original_name ?: "No Title Found").toString(),
                        Uri.encode(result.overview).toString(),
                        Uri.encode(result.first_air_date ?: "N/A").toString(),
                        Uri.encode(result.vote_average.toString()),
                        Uri.encode("575264")
                    )
                )
            }
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.large)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // TV series poster
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${result.backdrop_path}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TV series details
            Text(
                text = result.name ?: "No Title Found.",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Overview:",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = result.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Country : ${result.origin_country}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Average Vote: ${result.vote_average}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

