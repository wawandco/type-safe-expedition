package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

@Composable
fun HomeBottomBar(
    selectedSection: HomeSection, onSectionSelected: (HomeSection) -> Unit
) {
    val context = LocalContext.current
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
            .height(70.dp),
    ) {
        HomeSection.entries.forEach { section ->

            NavigationBarItem(
                selected = selectedSection == section,
                onClick = { onSectionSelected(section) },
                icon = {
                    Icon(
                        painter = painterResource(id = section.resId),
                        contentDescription = section.text.asString(context)
                    )
                },
                modifier = Modifier,
                label = {
                    Text(text = section.text.asString(context))
                }
            )
        }
    }
}

enum class HomeSection(val text: UiText, @DrawableRes val resId: Int) {
    POSTS(
        text = UiText.StringResource(resId = R.string.home_screen_posts_label),
        resId = R.drawable.post
    ),
    ALBUMS(
        text = UiText.StringResource(resId = R.string.home_screen_albums_label),
        resId = R.drawable.photo_album
    ),
    AUTHORS(
        text = UiText.StringResource(resId = R.string.home_screen_authors_label),
        resId = R.drawable.user_group
    ),
    MAP(
        text = UiText.StringResource(resId = R.string.home_screen_map_label),
        resId = R.drawable.map
    )
}