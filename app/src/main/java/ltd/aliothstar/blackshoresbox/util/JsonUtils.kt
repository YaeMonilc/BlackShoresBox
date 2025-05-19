package ltd.aliothstar.blackshoresbox.util

import org.json.JSONObject

fun String.nonStandardKuroResponseProcess(): String =
    JSONObject(this).apply {
        val element = get("data")

        if (element is String) {
            if (element.startsWith("{") && element.endsWith("}")) {
                remove("data")
                put("data", JSONObject(element))
            }
        }
    }.toString()