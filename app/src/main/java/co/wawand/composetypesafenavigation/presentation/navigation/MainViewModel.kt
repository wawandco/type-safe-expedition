package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(AppState.LOADING)
    val isLoggedIn: StateFlow<AppState> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.getUserId().let { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoggedIn.value = AppState.LOADING
                    }

                    is Resource.Success -> {
                        _isLoggedIn.value =
                            if (result.data == 0L) AppState.IDLE else AppState.LOGGED_IN
                    }

                    is Resource.Error -> {
                        _isLoggedIn.value = AppState.IDLE
                    }
                }
            }
        }
    }
}