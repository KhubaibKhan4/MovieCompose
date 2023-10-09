package com.codespacepro.moviecompose.components

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.R
import com.codespacepro.moviecompose.model.Result

@Composable
fun TrendingMovie(result: List<Result>, topBarText: String) {
    val context = LocalContext.current
    TrendingMovieList(result = result, context = context)

}

@Composable
fun TrendingMovieList(result: List<Result>, context: Context) {

    LazyColumn {
        items(result) { result ->
            TrendingMovieItem(result = result, context = context)
        }
    }

}

@Composable
fun TrendingMovieItem(result: Result, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Left side: Image with rating bar
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(237.dp)
        ) {
            // Movie Poster Image
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${result.poster_path}")
                    .crossfade(enable = true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            // Blurred Rating Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color.Transparent, Color(0xAA252836)),
                            startX = 100f,
                            endX = 100f
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                // Rating Icon and Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color(0xFFFF8700)
                    )
                    Text(
                        text = "${result.popularity}",
                        fontSize = 12.sp,
                        color = Color(0xFFFF8700),
                        letterSpacing = 0.12.sp,
                        fontWeight = FontWeight(600)
                    )
                }
            }
        }

        // Right side: Title, Date, Language, Vote Count
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp)
        ) {
            Text(
                text = "- ${result.title ?: "No Title Found..."}",
                fontSize = 22.sp,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.12.sp,
                fontWeight = FontWeight(600),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Release Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)

                )
                Text(
                    text = "- ${result.release_date ?: "Not Found.."}",
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    letterSpacing = 0.12.sp,
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            // Original Language
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.language),
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = "- ${result.original_language.uppercase()}",
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.LightGray,
                    letterSpacing = 0.12.sp,

                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            // Vote Count
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = "- ${result.vote_count ?: "Rating Not Found..."}",
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.LightGray,
                    letterSpacing = 0.12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            // Original Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = "${result.original_title ?: "No Title Found...."}",
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.LightGray,
                    letterSpacing = 0.12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            // Original Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Adult - ${result.adult ?: "Not Found..."}",
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.LightGray,
                    letterSpacing = 0.12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
