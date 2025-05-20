package ltd.aliothstar.blackshoresbox.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext

enum class GameResource(
    val path: String
) {
    IMG_UI_ITEM_BG("game/resource/img_ui_item_bg.png"),
    IMG_UI_ITEM_QUALITY_BLUE("game/resource/img_ui_item_quality_blue.png"),
    IMG_UI_ITEM_QUALITY_COLOR("game/resource/img_ui_item_quality_color.png"),
    IMG_UI_ITEM_QUALITY_GOLD("game/resource/img_ui_item_quality_gold.png"),
    IMG_UI_ITEM_QUALITY_GRAY("game/resource/img_ui_item_quality_gray.png"),
    IMG_UI_ITEM_QUALITY_GREEN("game/resource/img_ui_item_quality_green.png"),
    IMG_UI_ITEM_QUALITY_PURPLE("game/resource/img_ui_item_quality_purple.png"),
    IMG_UI_ITEM_QUALITY_RED("game/resource/img_ui_item_quality_red.png")
    ;
}

@Composable
fun painterGameImgResource(
    gameResource: GameResource
) = LocalContext.current.let { context ->
    remember {
        BitmapPainter(
            image = context.assets
                .open(gameResource.path)
                .readBytes()
                .decodeToImageBitmap()
        )
    }
}