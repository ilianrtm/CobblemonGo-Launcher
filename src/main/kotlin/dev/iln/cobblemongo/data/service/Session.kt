package dev.iln.cobblemongo.data.service

import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.data.config.ConfigStore
import dev.iln.cobblemongo.data.config.ConfigStore.config
import dev.iln.cobblemongo.resources.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object Session {

    private val _username = MutableStateFlow(config.value.username)
    val username = _username.asStateFlow()

    val isLoggedIn: Boolean
        get() = _username.value != null


    fun login(username: String, remember: Boolean) {
        _username.value = username
        ConfigStore.update { it.copy(username = username.takeIf { remember }) }

        Notifier.success(Res.string.notif_logged_in_title, Res.string.notif_logged_in_message, username)
    }
}