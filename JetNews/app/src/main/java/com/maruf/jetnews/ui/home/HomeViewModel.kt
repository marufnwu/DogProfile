package com.maruf.jetnews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.utils.ErrorMessage
import com.maruf.jetnews.R
import com.maruf.jetnews.data.Result
import com.maruf.jetnews.data.posts.PostRepository
import com.maruf.jetnews.model.Post
import com.maruf.jetnews.model.PostsFeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlinx.coroutines.flow.stateIn

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasPosts(
        val postsFeed: PostsFeed,
        val selectedPost: Post,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState
}

/**
 * An internal representation of the Home route state, in a raw form
 */
private data class HomeViewModelState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    /**
     * Converts this [HomeViewModelState] into a more strongly typed [HomeUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeUiState =
        if (postsFeed == null) {
            HomeUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeUiState.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id == selectedPostId
                } ?: postsFeed.highlightedPost,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}


class HomeViewModel(
    private val postsRepository: PostRepository,
    preSelectedPostId : String?
)  : ViewModel(){
    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
            selectedPostId = preSelectedPostId,
            isArticleOpen = preSelectedPostId != null
        )
    )

    val uiState = viewModelState
        .map (HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshPost()
        viewModelScope.launch {
            postsRepository.observeFavorites().collect{
                viewModelState.update {
                    it.copy(favorites = it.favorites)
                }
            }
        }
    }

    fun refreshPost(){
        viewModelState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val result = postsRepository.getPostsFeed()
            viewModelState.update {
                when(result){
                    is Result.Success -> it.copy(postsFeed = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(id = UUID.randomUUID().mostSignificantBits, messageId = R.string.load_error)
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }
}