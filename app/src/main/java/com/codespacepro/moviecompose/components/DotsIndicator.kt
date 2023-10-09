package com.codespacepro.moviecompose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    selectedDotColor: Color = Color.Black,
    unselectedDotColor: Color = Color.Gray
) {
    val pageCount = pagerState.pageCount
    val currentPage = pagerState.currentPage
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { pageIndex ->
            val dotColor = if (pageIndex == currentPage) selectedDotColor else unselectedDotColor
            val size = if (pageIndex == currentPage) 16.dp else dotSize
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    //.size(dotSize)
                    .width(size)
                    .background(color = dotColor, shape = CircleShape)
                    .padding(4.dp) // Adjust the padding as needed
            )
        }
    }
}