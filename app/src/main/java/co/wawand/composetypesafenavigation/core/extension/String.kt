package co.wawand.composetypesafenavigation.core.extension

/**
 * Truncates a string to a specified maximum length with various options
 *
 * @param maxLength The maximum length of the resulting string
 * @param ellipsis Optional ellipsis to append if text is truncated (defaults to "...")
 * @param preserveWords Whether to ensure the truncation doesn't cut words in half
 * @return Truncated string
 */
fun String.truncate(
    maxLength: Int,
    ellipsis: String = "...",
    preserveWords: Boolean = true
): String {
    // If the string is already shorter than or equal to maxLength, return it as-is
    if (length <= maxLength) return this

    // If we don't need to preserve words, simply cut and add ellipsis
    if (!preserveWords) {
        return substring(0, maxLength - ellipsis.length) + ellipsis
    }

    // Preserve words by finding the last space before maxLength
    val truncated = substring(0, maxLength - ellipsis.length)
    val lastSpaceIndex = truncated.lastIndexOf(' ')

    // If no space found, fall back to simple truncation
    return if (lastSpaceIndex == -1) {
        truncated + ellipsis
    } else {
        truncated.substring(0, lastSpaceIndex) + ellipsis
    }
}