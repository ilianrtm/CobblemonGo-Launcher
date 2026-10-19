package dev.iln.cobblemongo.core.log

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.StringResource
import java.util.concurrent.atomic.AtomicLong

enum class ToastLevel { Info, Success, Error }


data class AppToast(
    val id: Long,
    val level: ToastLevel,
    val title: StringResource,
    val message: StringResource,
    val args: List<Any>
)


object Notifier {

    private const val MAX_VISIBLE = 3

    private val nextId = AtomicLong()
    private val _toasts = MutableStateFlow<List<AppToast>>(emptyList())
    val toasts = _toasts.asStateFlow()


    fun info(title: StringResource, message: StringResource, vararg args: Any) {
        push(ToastLevel.Info, title, message, args.toList())

    }


    fun success(title: StringResource, message: StringResource, vararg args: Any) {
        push(ToastLevel.Success, title, message, args.toList())
    }


    fun error(title: StringResource, message: StringResource) {
        push(ToastLevel.Error, title, message, emptyList())
    }


    fun dismiss(id: Long) {
        _toasts.update { list -> list.filterNot { it.id == id } }
    }


    private fun push(level: ToastLevel, title: StringResource, message: StringResource, messageArgs: List<Any>) {
        val toast = AppToast(nextId.incrementAndGet(), level, title, message, messageArgs)
        _toasts.update { (it + toast).takeLast(MAX_VISIBLE) }
    }
}