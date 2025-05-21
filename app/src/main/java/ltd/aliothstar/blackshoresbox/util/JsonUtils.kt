package ltd.aliothstar.blackshoresbox.util

import org.json.JSONObject

fun String.nonStandardKuroResponseProcess(): String =
    JSONObject(this).apply {
        try {
            val element = get("data")

            if (element is String) {
                if (element.startsWith("{") && element.endsWith("}")) {
                    remove("data")
                    put("data", JSONObject(element))
                }
            }
        } catch (_: Exception) {

        }
    }.toString()