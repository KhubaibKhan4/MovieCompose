package com.codespacepro.moviecompose.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.moviecompose.model.Result

@Composable
fun TrendingTV() {

}

@Composable
fun TrendingTVList(result: List<Result>, topBarText: String) {
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
        items(result) { result ->
            TrendingTVItem(result = result)
        }
    }
}

@Composable
fun TrendingTVItem(result: Result) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
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
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
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
                    color = Color.White,
                    letterSpacing = 0.12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Action", // You can make this text dynamic based on the result
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
