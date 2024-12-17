package co.wawand.composetypesafenavigation.presentation.screens.details.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.core.extension.truncate
import co.wawand.composetypesafenavigation.domain.model.Post

@Composable
fun PostDetails(post: Post?, relatedPosts: List<Post>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = post?.title ?: stringResource(id = R.string.home_screen_unknown_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = post?.body ?: stringResource(id = R.string.home_screen_unknown_label),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(id = R.string.home_screen_author_label)} ${
                    post?.author?.name ?: stringResource(
                        id = R.string.home_screen_unknown_label
                    )
                }",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(id = R.string.home_screen_author_email_label)} ${
                    post?.author?.email ?: stringResource(
                        id = R.string.home_screen_unknown_label
                    )
                }",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.details_post_screen_related_posts_label),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )

        if (relatedPosts.isEmpty()) {
            Text(
                text = "No related posts found",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        } else {
            LazyRow(
                modifier = Modifier.padding(16.dp)
            ) {
                items(relatedPosts.size) { index ->
                    ElevatedCard(
                        shape = RoundedCornerShape(4.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(120.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = relatedPosts[index].title.truncate(maxLength = 12),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleSmall,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = relatedPosts[index].body.truncate(maxLength = 24),
                            maxLines = 1,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}