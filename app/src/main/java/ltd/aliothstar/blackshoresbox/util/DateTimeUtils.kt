package ltd.aliothstar.blackshoresbox.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.kuroWikiDateTimeToTimestamp() =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).let {
        it.parse(this)?.time ?: System.currentTimeMillis()
    }

fun timeStampToProcess(
    startTimestamp: Long,
    endTimestamp: Long
) = ((System.currentTimeMillis() - startTimestamp) / (endTimestamp - startTimestamp)).toFloat()
