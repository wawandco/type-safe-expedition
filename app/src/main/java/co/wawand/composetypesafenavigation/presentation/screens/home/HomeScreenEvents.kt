package co.wawand.composetypesafenavigation.presentation.screens.home

sealed class HomeScreenEvents {
    data class OnHomeSectionChanged(val section: HomeSection) : HomeScreenEvents()
    data object OnRefreshPosts : HomeScreenEvents()
    data object OnRefreshAlbums : HomeScreenEvents()
    data object OnRefreshAuthors : HomeScreenEvents()
    data object OnLoadLocalAlbums : HomeScreenEvents()
    data object OnLoadLocalAuthors : HomeScreenEvents()
}