package com.codespacepro.moviecompose.components

import android.content.Context
import android.net.Uri
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.model.person.ResultX
import com.codespacepro.moviecompose.navigation.navgraph.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonProfileList(
    person: List<ResultX>,
    topBarText: String,
    context: Context,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = topBarText,
            fontSize = 16.sp,
            color = Color(0xFFFFFFFF),
            letterSpacing = 0.12.sp,
            fontWeight = FontWeight(600),
        )
        Text(
            text = "See All",
            fontSize = 14.sp,
            color = Color(0xFF12CDD9),
            letterSpacing = 0.12.sp,
            fontWeight = FontWeight(500),
        )
    }
    LazyRow {
        items(person) { person ->
            PersonProfileItem(
                result = person,
                context = context,
                navController = navController,
                modifier = Modifier.animateItemPlacement(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.DampingRatioMediumBouncy,
                        visibilityThreshold = null
                    )
                )
            )
        }
    }
}

@Composable
fun PersonProfileItem(
    result: ResultX,
    context: Context,
    navController: NavHostController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    Screen.ActorDetail.passData(
                        Uri.encode(result.profile_path),
                        Uri.encode(result.name),
                        Uri.encode(result.known_for_department),
                        Uri.encode(result.gender.toString()),
                        Uri.encode(result.popularity.toString()),
                        Uri.encode(result.known_for[0].title),
                        Uri.encode(result.known_for[0].backdrop_path),
                        Uri.encode(result.known_for[0].overview),
                        Uri.encode(result.known_for[0].release_date),
                        Uri.encode(result.known_for[0].vote_count.toString()),
                    )

                )
            },

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(10.dp)
                .clip(
                    shape = CircleShape
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${result.profile_path}")
                    .crossfade(enable = true)
                    .build(), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "${result.name}",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            softWrap = true
        )
    }

}