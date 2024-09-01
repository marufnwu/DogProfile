package com.maruf.jetnews.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maruf.jetnews.R
import com.maruf.jetnews.ui.theme.JetNewsTheme



@Composable
fun AppDrawer(
    currentRoute : String,
    navigateToHome : () -> Unit,
    navigateToInterests : () -> Unit,
    closeDrawer : () -> Unit,
    modifier: Modifier = Modifier
){
    ModalDrawerSheet(modifier) {
        JetNewsLogo(modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp))

        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.home_title)) },
            icon = {Icon(Icons.Filled.Home, null)},
            selected = currentRoute == JetnewsDestinations.HOME_ROUTE,
            onClick = navigateToHome,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.interests_title)) },
            icon = {Icon(Icons.Filled.List, null)},
            selected = currentRoute == JetnewsDestinations.INTERESTS_ROUTE,
            onClick = navigateToInterests,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}


//@Preview(showBackground = true)
@Composable
fun JetNewsLogo(modifier: Modifier = Modifier){
   Row(modifier = modifier) {
       Icon(painterResource(id = R.drawable.ic_jetnews_logo), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
       Spacer(modifier = Modifier.width(8.dp))
       Icon(painterResource(id = R.drawable.ic_jetnews_wordmark), contentDescription = stringResource(
           id = R.string.app_name
       ), tint = MaterialTheme.colorScheme.onSurfaceVariant)
   }
}


@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    JetNewsTheme {
        AppDrawer(
            currentRoute = JetnewsDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToInterests = {},
            closeDrawer = { }
        )
    }
}


