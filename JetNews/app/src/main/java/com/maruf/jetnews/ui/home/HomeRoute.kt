package com.maruf.jetnews.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    openDrawer : () -> Unit,
    snackBarHostState : SnackbarHostState = remember {SnackbarHostState()},
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val homeListLazyListState = rememberLazyListState()
    val articleDetailLazyListStates = when (uiState) {
        is HomeUiState.HasPosts -> (uiState as HomeUiState.HasPosts).postsFeed.allPosts
        is HomeUiState.NoPosts -> emptyList()
    }.associate { post ->
        key(post.id) {
            post.id to rememberLazyListState()
        }
    }

    val homeScreenType = getHomeScreenType(uiState)

}

/**
 * A precise enumeration of which type of screen to display at the home route.
 *
 * There are 3 options:
 * - [FeedWithArticleDetails], which displays both a list of all articles and a specific article.
 * - [Feed], which displays just the list of all articles
 * - [ArticleDetails], which displays just a specific article.
 */
private enum class HomeScreenType {
    Feed,
    ArticleDetails
}

/**
 * Returns the current [HomeScreenType] to display, based on whether or not the screen is expanded
 * and the [HomeUiState].
 */
@Composable
private fun getHomeScreenType(
    uiState: HomeUiState
): HomeScreenType {
    return when (uiState) {
        is HomeUiState.HasPosts -> {
            if (uiState.isArticleOpen) {
                HomeScreenType.ArticleDetails
            } else {
                HomeScreenType.Feed
            }
        }

        is HomeUiState.NoPosts -> HomeScreenType.Feed
    }
}

