package ltd.aliothstar.blackshoresbox.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext

enum class GameResource(
    val path: String
) {

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