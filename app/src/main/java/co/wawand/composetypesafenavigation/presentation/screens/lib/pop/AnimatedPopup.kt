package co.wawand.composetypesafenavigation.presentation.screens.lib.pop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AnimatedPopup(
    onDismissRequest: () -> Unit,
    alignment: Alignment = Alignment.TopStart,
    offset: IntOffset = IntOffset(0, 0),
    properties: PopupProperties = PopupProperties(),
    content: @Composable (AnimatedTransitionDialogHelper) -> Unit
) {

    val onDismissSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        launch {
            animateTrigger.value = true
        }

        launch {
            onDismissSharedFlow.asSharedFlow().collectLatest {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }

    }

    Popup(
        alignment, offset,
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        },
        properties,
    ) {
        AnimatedFadeInOutTransition(visible = animateTrigger.value) {
            content(AnimatedTransitionDialogHelper(coroutineScope, onDismissSharedFlow))
        }

    }
}

internal suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>, onDismissRequest: () -> Unit
) {
    animateTrigger.value = false
    delay(500)
    onDismissRequest()
}

@Composable
internal fun AnimatedFadeInOutTransition(
    visible: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(600),
        ),
        exit = fadeOut(
            animationSpec = tween(600),
        ),
        content = content,
    )
}

class AnimatedTransitionDialogHelper(
    private val coroutineScope: CoroutineScope, private val onDismissFlow: MutableSharedFlow<Any>
) {

    fun triggerAnimatedDismiss() {
        coroutineScope.launch {
            onDismissFlow.emit(Any())
        }
    }
}