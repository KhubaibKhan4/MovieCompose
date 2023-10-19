package com.codespacepro.moviecompose.components

import android.content.Context
import android.net.Uri
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.model.Result
import com.codespacepro.moviecompose.navigation.navgraph.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSlider(
    result: List<Result>,
    context: Context,
    scope: CoroutineScope,
    navController: NavHostController
) {

    val state =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
            result.size
        }
    val pageOffset = state.currentPageOffsetFraction
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(state = state) { page ->
            val pageItem by remember {
                mutableIntStateOf(page)
            }
            val currentItem = result[pageItem]
            TopSliderItem(result = currentItem, context = context, state, navController)
        }
        DotsIndicator(
            pagerState = state,
            modifier = Modifier.padding(8.dp),
            dotSize = 8.dp,
            selectedDotColor = Color.Red,
            unselectedDotColor = Color.White
        )
        // Automatic slide every 200 milliseconds
        LaunchedEffect(state.currentPage) {
            while (true) {
                delay(4000)
                val nextPage = (state.currentPage + 1) % result.size
                state.animateScrollToPage(
                    nextPage,
                    pageOffsetFraction = pageOffset,
                    animationSpec = spring(
                        dampingRatio = 1f,
                        stiffness = 1f,
                        visibilityThreshold = pageOffset
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSliderItem(
    result: Result,
    context: Context,
    pagerState: PagerState,
    navController: NavHostController
) {

    val pageOffset = pagerState.currentPageOffsetFraction

    // Calculate the horizontal offset for the current image
    val offsetX = (pageOffset * 295.dp).coerceIn((-295).dp, 295.dp)

    // Calculate the scale factor to zoom in/out the current image
    val scaleFactor = 0.5f + 0.5f * (1 - pageOffset.absoluteValue)

    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .width(295.dp)
            .height(154.dp)
            .offset(x = offsetX)
            .scale(scaleX = scaleFactor, scaleY = scaleFactor)
            .clickable {
                navController.navigate(
                    Screen.MovieDetail.passData(
                        Uri.encode(result.poster_path),
                        Uri.encode(result.original_title),
                        Uri.encode(result.overview),
                        Uri.encode(result.release_date),
                        Uri.encode(result.vote_average.toString()),
                        Uri.encode(result.id.toString()),
                    )
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data("https://image.tmdb.org/t/p/w500${result.backdrop_path}")
                .crossfade(enable = true)
                .build(), contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp, start = 4.dp, end = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${result.title}",
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 22.sp,
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.12.sp,
            )
            val date: String = convertDate(result.release_date)
            Text(
                text = "${result.release_date.let { convertDate(it) }}",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color(0xFFEBEBEF),
            )
        }
    }
}

fun convertDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM d", Locale.getDefault())
    val dateObject: Date? = inputFormat.parse(date)
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dayName = dayFormat.format(dateObject)
    val monthName = outputFormat.format(dateObject)
    return "$dayName | $monthName"
}