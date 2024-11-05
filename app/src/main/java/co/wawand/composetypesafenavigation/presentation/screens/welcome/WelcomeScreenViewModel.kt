package co.wawand.composetypesafenavigation.presentation.screens.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.Constant.MIN_NAME_LENGTH
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.User
import co.wawand.composetypesafenavigation.domain.usecase.SignedInUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val signedInUseCase: SignedInUseCase,
) : ViewModel() {
    var state by mutableStateOf(WelcomeScreenState())
        private set

    fun onEvent(event: WelcomeScreenEvents) {
        when (event) {
            is WelcomeScreenEvents.UpdateName -> {
                updateUserName(UiText.DynamicString(event.q))
            }

            is WelcomeScreenEvents.PersistName -> {
                persistUserName(event.q)
            }

            is WelcomeScreenEvents.ClearError -> {
                clearError()
            }
        }
    }


    private fun updateUserName(str: UiText) {
        state = state.copy(name = str)
    }

    private fun persistUserName(str: String) {
        if (str.length < 3) {
            state = state.copy(
                error = UiText.StringResource(
                    R.string.welcome_screen_min_name_length_error,
                    MIN_NAME_LENGTH
                )
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            signedInUseCase.invoke(User(name = str)).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(
                                isLoading = false,
                                success = true
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                        )
                    }
                }
            }
        }
    }

    private fun clearError() {
        state = state.copy(error = UiText.DynamicString(""))
    }
}