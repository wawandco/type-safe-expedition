package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.usecase.GetAlbumsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetAuthorsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetLocalAlbumsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetLocalAuthorsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetLocalPostsUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetPostsUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getLocalPostsUserCase: GetLocalPostsUseCase,
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getLocalAlbumsUseCase: GetLocalAlbumsUseCase,
    private val getAuthorsUseCase: GetAuthorsUseCase,
    private val getLocalAuthorsUseCase: GetLocalAuthorsUseCase,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeScreenState())
    val uiState = viewModelState.asStateFlow()

    init {
        getLocalPosts()
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnRefreshPosts -> getAndSyncPosts()

            is HomeScreenEvents.OnHomeSectionChanged -> onHomeSectionChanged(event.section)

            is HomeScreenEvents.OnRefreshAlbums -> getAndSyncAlbums()

            is HomeScreenEvents.OnLoadLocalAlbums -> getLocalAlbums()

            is HomeScreenEvents.OnRefreshAuthors -> getAndSyncAuthors()

            is HomeScreenEvents.OnLoadLocalAuthors -> getLocalAuthors()
        }
    }

    private fun onHomeSectionChanged(section: HomeSection) {
        when (section) {
            HomeSection.POSTS -> {
                viewModelState.update {
                    it.copy(
                        currentSection = HomeSection.POSTS
                    )
                }
                getLocalPosts()
            }

            HomeSection.ALBUMS -> {
                viewModelState.update {
                    it.copy(
                        currentSection = HomeSection.ALBUMS
                    )
                }
                getLocalAlbums()
            }

            HomeSection.AUTHORS -> {
                viewModelState.update {
                    it.copy(
                        currentSection = HomeSection.AUTHORS
                    )
                }
                getLocalAuthors()
            }

            HomeSection.MAP -> viewModelState.update {
                it.copy(
                    currentSection = HomeSection.MAP
                )
            }
        }
    }

    private fun getLocalPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            getLocalPostsUserCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    posts = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAndSyncPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            getPostsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    posts = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getLocalAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            getLocalAlbumsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    albums = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAndSyncAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            getAlbumsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    albums = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getLocalAuthors() {
        viewModelScope.launch(Dispatchers.IO) {
            getLocalAuthorsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    authors = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAndSyncAuthors() {
        viewModelScope.launch(Dispatchers.IO) {
            getAuthorsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    authors = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }
                    }
                }
            }
        }
    }

}