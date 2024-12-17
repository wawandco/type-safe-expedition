package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.core.extension.truncate
import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticPosts

@Composable
fun PostList(
    postList: List<Post> = emptyList(),
    onPostClick: (Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (postList.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.home_screen_empty_list),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(postList.size) { index ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 1.dp)
                            .height(120.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = postList[index].title.truncate(maxLength = 12),
                                maxLines = 1,
                                style = MaterialTheme.typography.titleMedium,
                                overflow = TextOverflow.Ellipsis
                            )

                            TextButton(onClick = { onPostClick(postList[index].id) }) {
                                Text(
                                    text = "Read more", style = MaterialTheme.typography.labelMedium
                                )
                            }

                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = postList[index].body,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "${stringResource(id = R.string.home_screen_author_label)} ${
                                    postList[index].author?.name ?: stringResource(
                                        id = R.string.home_screen_unknown_label
                                    )
                                }",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PostListPreviewEmptyList() {
    PostList()
}

@Preview
@Composable
fun PostListPreview() {
    PostList(postList = generateStaticPosts())
}