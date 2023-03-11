package com.manish.dkb.presentation.ui.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manish.dkb.presentation.ui.compose.ui.AlbumListScreen
import com.manish.dkb.presentation.ui.compose.ui.AlbumScreen.AlbumDetailScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.AlbumList.path
    ) {
        addAlbumListScreen(navController, this)

        addAlbumDetailScreen(navController, this)
    }
}

fun addAlbumListScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.AlbumList.path) {
        AlbumListScreen(
            navigateToAlbumDetail = { id->
                navController.navigate(NavRoute.AlbumDetail.withArgs(id.toString()))
            }
        )
    }
}

fun addAlbumDetailScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.AlbumDetail.withArgsFormat(NavRoute.AlbumDetail.id),
        arguments = listOf(
            navArgument(NavRoute.AlbumDetail.id) {
                type = NavType.IntType
            }
        )
        ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        AlbumDetailScreen(
            id = args?.getInt(NavRoute.AlbumDetail.id)!!,
            popBackStack = { navController.popBackStack() },
            popUpToLogin= { popUpToLogin(navController) },
        )
    }
}

private fun popUpToLogin(navController: NavHostController) {
    navController.popBackStack(NavRoute.AlbumList.path, inclusive = false)
}

