package com.codespacepro.moviecompose.navigation.navgraph

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

    object MovieDetail : Screen(
        route = "detail/{post_image}/{title}/{overview}/{pubDate}/{voteAverage}/{id}",
        title = "Movie Detail",
        icon = R.drawable.tv
    ) {
        fun passData(
            posterImage: String,
            title: String,
            overview: String,
            pubDate: String,
            voteAverage: String,
            id: String
        ): String {

            return "detail/$posterImage/$title/$overview/$pubDate/$voteAverage/$id"
        }
    }

    object ActorDetail : Screen(
        route = "actor_details/{profile_path}/{name}/{known_for_department}/{gender}/{popularity}",
        title = "Actor Detail",
        icon = R.drawable.movie

    ) {
        fun passData(
            profile_path: String,
            name: String,
            known_for_department: String,
            gender: String,
            popularity: String
        ): String {
            return "actor_details/$profile_path/$name/$known_for_department/$gender/$popularity"
        }
    }
}