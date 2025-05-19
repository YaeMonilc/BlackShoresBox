package ltd.aliothstar.blackshoresbox.util

import kotlin.random.Random

private const val randomText = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

fun randomText(
    length: Int,
    upperCase: Boolean = false
) = StringBuilder().apply {
    repeat(length) {
        append(randomText[Random.nextInt(randomText.length)])
    }
}.toString().let {
    if (upperCase) it.uppercase() else it
}

