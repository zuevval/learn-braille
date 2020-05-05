package com.github.braillesystems.learnbraille.utils

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

inline fun <T, R> T?.side(block: (T) -> R) {
    if (this != null) block(this)
}

operator fun MatchGroupCollection.component1() = get(0)
operator fun MatchGroupCollection.component2() = get(1)
operator fun MatchGroupCollection.component3() = get(2)
operator fun MatchGroupCollection.component4() = get(3)
operator fun MatchGroupCollection.component5() = get(4)

val Any?.devnull: Unit get() {}

fun String.removeHtmlMarkup() = Regex("""<[^>]*>""").replace(this, "")

// TODO fix warnings

fun <T> stringify(s: SerializationStrategy<T>, obj: T) = Json.stringify(s, obj)

fun <T> parse(d: DeserializationStrategy<T>, s: String) = Json.parse(d, s)
