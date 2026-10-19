package dev.iln.cobblemongo.ui.screen.main.components.toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.core.log.ToastLevel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ToastSection(modifier: Modifier = Modifier) {
    val toasts by Notifier.toasts.collectAsStateWithLifecycle()


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End,
        userScrollEnabled = false
    ) {
        items(toasts, key = { it.id }) {
            LaunchedEffect(it.id) {
                val duration = if (it.level == ToastLevel.Error) 6000L else 4000L
                delay(duration.milliseconds)

                Notifier.dismiss(it.id)
            }


            ToastCard(
                toast = it,
                onDismiss = { Notifier.dismiss(it.id) },
                modifier = Modifier.animateItem()
            )
        }
    }
}