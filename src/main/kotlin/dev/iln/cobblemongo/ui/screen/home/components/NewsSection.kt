package dev.iln.cobblemongo.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.iln.cobblemongo.data.config.external.NewsCategory
import dev.iln.cobblemongo.data.config.external.NewsItem
import dev.iln.cobblemongo.resources.*
import dev.iln.cobblemongo.ui.components.AppText
import dev.iln.cobblemongo.ui.components.extention.pixelPanel
import dev.iln.cobblemongo.ui.theme.AppTheme.colors
import dev.iln.cobblemongo.ui.theme.AppTheme.shapes
import dev.iln.cobblemongo.ui.theme.AppTheme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun NewsSection(
    news: List<NewsItem>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .pixelPanel(shapes.large, colors.purple300, 6.dp)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AppText(
            text = stringResource(Res.string.news_title),
            modifier = Modifier.padding(bottom = 4.dp),
            style = typography.headline
        )

        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState())
        ) {
            if (news.isEmpty()) {
                AppText(
                    text = stringResource(Res.string.news_empty),
                    style = typography.caption,
                    color = colors.purple100
                )
            }

            news.forEach { item -> NewsArticle(item) }
        }
    }
}


@Composable
private fun NewsArticle(item: NewsItem) {
    Column {
        Box(Modifier.fillMaxWidth().height(2.dp).background(colors.purple400))

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(Modifier.padding(top = 4.dp).size(10.dp).background(item.category.color))

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                AppText(
                    text = stringResource(Res.string.news_meta, item.category.label, item.date),
                    style = typography.caption,
                    color = colors.purple100
                )

                AppText(
                    text = item.title,
                    style = typography.bodySemiBold,
                    maxLines = 1
                )

                AppText(
                    text = item.description,
                    style = typography.caption,
                    color = colors.purple100,
                    maxLines = 2
                )
            }
        }
    }
}


private val NewsCategory.label: String
    @Composable get() = when (this) {
        NewsCategory.Modpack -> stringResource(Res.string.news_category_modpack)
        NewsCategory.Event -> stringResource(Res.string.news_category_event)
        NewsCategory.Server -> stringResource(Res.string.news_category_server)
    }


private val NewsCategory.color: Color
    @Composable
    get() = when (this) {
        NewsCategory.Modpack -> colors.teal400
        NewsCategory.Event -> colors.orange200
        NewsCategory.Server -> colors.yellow100
    }