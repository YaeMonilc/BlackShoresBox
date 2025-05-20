package ltd.aliothstar.blackshoresbox.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ltd.aliothstar.blackshoresbox.util.GameResource
import ltd.aliothstar.blackshoresbox.util.painterGameImgResource

enum class ItemLevel {
    GRAY,
    GREEN,
    BLUE,
    PURPLE,
    GOLD,
    RED,
    COLOR
}

@Composable
fun GameItemCard(
    modifier: Modifier = Modifier,
    level: ItemLevel,
    resource: Painter,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .size(
                width = 125.dp,
                height = 175.dp
            )
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterGameImgResource(GameResource.IMG_UI_ITEM_BG),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = resource,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .align(Alignment.BottomCenter),
                    painter = painterGameImgResource(
                        gameResource = when (level) {
                            ItemLevel.GRAY -> GameResource.IMG_UI_ITEM_QUALITY_GRAY
                            ItemLevel.GREEN -> GameResource.IMG_UI_ITEM_QUALITY_GREEN
                            ItemLevel.BLUE -> GameResource.IMG_UI_ITEM_QUALITY_BLUE
                            ItemLevel.PURPLE -> GameResource.IMG_UI_ITEM_QUALITY_PURPLE
                            ItemLevel.GOLD -> GameResource.IMG_UI_ITEM_QUALITY_GOLD
                            ItemLevel.RED -> GameResource.IMG_UI_ITEM_QUALITY_RED
                            ItemLevel.COLOR -> GameResource.IMG_UI_ITEM_QUALITY_COLOR
                        }
                    ),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                content()
            }
        }
    }
}