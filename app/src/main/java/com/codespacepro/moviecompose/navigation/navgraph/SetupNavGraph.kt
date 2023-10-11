package com.codespacepro.moviecompose.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codespacepro.moviecompose.components.ActorDetail
import com.codespacepro.moviecompose.components.MovieDetail
import com.codespacepro.moviecompose.screen.HomeScreen
import com.codespacepro.moviecompose.screen.Movies
import com.codespacepro.moviecompose.screen.Popular
import com.codespacepro.moviecompose.screen.TV
import com.codespacepro.moviecompose.screen.Trending

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Trending.route) {
            Trending(navController)
        }
        composable(route = Screen.Popular.route) {
            Popular(navController)
        }
        composable(route = Screen.Movies.route) {
            Movies(navController)
        }
        composable(route = Screen.Tv.route) {
            TV(navController)
        }
        composable(route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument("post_image") {
                    type = NavType.StringType
                },
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("overview") {
                    type = NavType.StringType
                },
                navArgument("pubDate") {
                    type = NavType.StringType
                },
                navArgument("voteAverage") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val postImage = it.arguments?.getString("post_image")
            val title = it.arguments?.getString("title")
            val overview = it.arguments?.getString("overview")
            val pubDate = it.arguments?.getString("pubDate")
            val voteAverage = it.arguments?.getString("voteAverage")
            val id = it.arguments?.getString("id")
            MovieDetail(navController, postImage, title, overview, pubDate, voteAverage, id)
        }

        composable(route = Screen.ActorDetail.route,
            arguments = listOf(
                navArgument("profile_path") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("known_for_department") {
                    type = NavType.StringType
                },
                navArgument("gender") {
                    type = NavType.StringType
                },
                navArgument("popularity") {
                    type = NavType.StringType
                },
                navArgument("know_for") {
                    type = NavType.StringType
                }, navArgument("backdrop_path") {
                    type = NavType.StringType
                }
                , navArgument("overview") {
                    type = NavType.StringType
                }
                , navArgument("release_date") {
                    type = NavType.StringType
                }
                , navArgument("vote_count") {
                    type = NavType.StringType
                }
            )
        ) {
            val profile_path = it.arguments?.getString("profile_path")
            val name = it.arguments?.getString("name")
            val known_for_department = it.arguments?.getString("known_for_department")
            val gender = it.arguments?.getString("gender")
            val popularity = it.arguments?.getString("popularity")
            val know_for = it.arguments?.getString("know_for")
            val backdrop_path = it.arguments?.getString("backdrop_path")
            val overview = it.arguments?.getString("overview")
            val release_date = it.arguments?.getString("release_date")
            val vote_count = it.arguments?.getString("vote_count")

            ActorDetail(
                navController,
                profile_path,
                name,
                known_for_department,
                gender,
                popularity,
                know_for,
                backdrop_path,
                overview,
                release_date,
                vote_count
            )
        }
    }

}