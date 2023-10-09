package com.codespacepro.moviecompose.navigation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.codespacepro.moviecompose.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen(
        route = "home",
        title = "Home",
        icon = R.drawable.home
    )
    object Trending : Screen(
        route = "trending",
        title = "Trending",
        icon = R.drawable.trending
    )
    object Popular : Screen(
        route = "popular",
        title = "Popular",
        icon = R.drawable.movie
    )

    object Movies : Screen(
        route = "movies",
        title = "Movies",
        icon = R.drawable.movies
    )

    object Tv : Screen(
        route = "tv",
        title = "Tv",
        icon = R.drawable.tv
    )
}