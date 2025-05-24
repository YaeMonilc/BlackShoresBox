package ltd.aliothstar.blackshoresbox.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AreaContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        ProvideTextStyle(
            value = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 13.sp
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 2.dp
                    )
            ) {
                title()
            }
        }
        Spacer(
            modifier = Modifier
                .requiredHeight(8.dp)
        )
        content()
    }
}

@Composable
fun CardAreaContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    AreaContent(
        modifier = modifier,
        title = title,
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors().copy(
                    contentColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                content()
            }
        }
    )
}

@Composable
fun OutlinedCardAreaContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    AreaContent(
        modifier = modifier,
        title = title,
        content = {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    )
}
