
package com.maruf.jetnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maruf.jetnews.data.AppContainer

@Composable
fun JetnewsNavGraph(
    appContainer : AppContainer,
    modifier: Modifier = Modifier,
    navController : NavHostController,
    openDrawer : () -> Unit,
    startDestination : String  = JetnewsDestinations.HOME_ROUTE
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(
            route = JetnewsDestinations.HOME_ROUTE
        ){

        }
    }
}