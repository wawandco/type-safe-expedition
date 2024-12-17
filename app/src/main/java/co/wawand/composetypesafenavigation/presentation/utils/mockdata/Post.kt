package co.wawand.composetypesafenavigation.presentation.utils.mockdata

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import co.wawand.composetypesafenavigation.domain.model.Author
import co.wawand.composetypesafenavigation.domain.model.Post

fun generateStaticPosts(): List<Post> {
    val authors = generateStaticAuthors()

    return listOf(
        Post(
            id = 1,
            title = "First Post",
            body = "This is the content of the first post. It contains some text to test the display.",
            author = authors[0]
        ),
        Post(
            id = 2,
            title = "Learning Kotlin",
            body = "Kotlin is a modern programming language that makes developers happier. Let's learn together!",
            author = authors[1]
        ),
        Post(
            id = 3,
            title = "Android Development",
            body = "Building Android apps is fun and rewarding. Here are some tips to get started.",
            author = authors[2]
        ),
        Post(
            id = 4,
            title = "Jetpack Compose",
            body = "Jetpack Compose is the future of Android UI development. It makes building UIs much easier.",
            author = authors[0]
        ),
        Post(
            id = 5,
            title = "Software Architecture",
            body = "Good architecture is crucial for maintaining and scaling your applications.",
            author = authors[1]
        )
    )
}

fun generateStaticAuthors(): List<Author> {
    return listOf(
        Author(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john.doe@example.com"
        ),
        Author(
            id = 2,
            name = "Jane Smith",
            username = "janesmith",
            email = "jane.smith@example.com"
        ),
        Author(
            id = 3,
            name = "Robert Johnson",
            username = "rjohnson",
            email = "robert.johnson@example.com"
        )
    )
}