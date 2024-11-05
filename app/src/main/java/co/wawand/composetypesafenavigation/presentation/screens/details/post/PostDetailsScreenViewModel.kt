package co.wawand.composetypesafenavigation.presentation.screens.details.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.usecase.GetPostDetailsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetRelatedPostsUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsScreenViewModel @Inject constructor(
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getRelatedPostsUseCase: GetRelatedPostsUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(PostDetailsScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(event: PostDetailsScreenEvents) {
        when (event) {
            is PostDetailsScreenEvents.LoadPostDetails -> getLocalPostDetails(event.postId)

            is PostDetailsScreenEvents.LoadRelatedPosts -> getRelatedPosts(event.userId)

        }
    }

    private fun getLocalPostDetails(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getPostDetailsUseCase.invoke(postId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoadingPost = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        viewModelState.update {
                            it.copy(
                                post = result.data,
                                isLoadingPost = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR),
                                isLoadingPost = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getRelatedPosts(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getRelatedPostsUseCase.invoke(userId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoadingRelatedPosts = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        viewModelState.update {
                            it.copy(
                                relatedPosts = result.data ?: emptyList(),
                                isLoadingRelatedPosts = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR),
                                isLoadingRelatedPosts = false
                            )
                        }
                    }
                }
            }
        }
    }
}