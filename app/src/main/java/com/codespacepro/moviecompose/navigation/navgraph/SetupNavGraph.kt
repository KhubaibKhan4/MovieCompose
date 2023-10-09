package com.codespacepro.moviecompose.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codespacepro.moviecompose.screen.HomeScreen
import com.codespacepro.moviecompose.screen.Movies
import com.codespacepro.moviecompose.screen.Popular
import com.codespacepro.moviecompose.screen.TV
import com.codespacepro.moviecompose.screen.Trending

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Trending.route) {
            Trending()
        }
        composable(route = Screen.Popular.route) {
            Popular()
        }
        composable(route = Screen.Movies.route) {
            Movies()
        }
        composable(route = Screen.Tv.route) {
            TV()
        }
    }

}