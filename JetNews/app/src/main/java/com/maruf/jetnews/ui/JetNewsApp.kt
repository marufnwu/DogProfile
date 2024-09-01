package com.maruf.jetnews.ui

import android.app.Application
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maruf.jetnews.App
import com.maruf.jetnews.data.AppContainer
import com.maruf.jetnews.ui.theme.JetNewsTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun JetNewsApp(appContainer: AppContainer? = null){
    JetNewsTheme {

        val drawerState : DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navController = rememberNavController()
        val navigationActions = remember (navController) {
            JetnewsNavigationActions(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route ?: JetnewsDestinations.HOME_ROUTE
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            gesturesEnabled = false,
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToInterests = navigationActions.navigateToInterests,
                    closeDrawer = { coroutineScope.launch { drawerState.close()  }}
                )
            }
        ) {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetNewsPreview() {
   JetNewsApp()
}