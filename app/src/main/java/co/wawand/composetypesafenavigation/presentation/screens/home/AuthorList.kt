package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.domain.model.Author
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticAuthors

@Composable
fun AuthorList(authors: List<Author>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (authors.isEmpty()) {
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
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(authors.size) { index ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 1.dp)
                            .height(200.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(90.dp),
                                painter = painterResource(R.drawable.taunt),
                                contentDescription = authors[index].name,
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                            )

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = authors[index].name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                )

                                Text(
                                    text = authors[index].email,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }

                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = stringResource(id = R.string.home_screen_albums_label),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = authors[index].albums.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = stringResource(id = R.string.home_screen_posts_label),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = authors[index].posts.toString(),
                                style = MaterialTheme.typography.bodySmall
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
fun AuthorListPreview() {
    AuthorList(generateStaticAuthors())
}