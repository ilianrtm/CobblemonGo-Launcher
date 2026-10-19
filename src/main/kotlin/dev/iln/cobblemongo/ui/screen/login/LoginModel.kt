package dev.iln.cobblemongo.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.iln.cobblemongo.data.service.Session

sealed interface UsernameStatus {
    data object Empty : UsernameStatus
    data class TooShort(val missingCharacters: Int) : UsernameStatus
    data object InvalidCharacters : UsernameStatus
    data object Valid : UsernameStatus
}


class LoginModel {

    companion object {
        const val MIN_LENGTH = 3
        const val MAX_LENGTH = 16

        private val usernameRegex = Regex("^[A-Za-z0-9_]{$MIN_LENGTH,$MAX_LENGTH}$")
    }


    var username by mutableStateOf(Session.username.value.orEmpty())
        private set

    var remember by mutableStateOf(true)
        private set

    val status: UsernameStatus
        get() = when {
            username.isEmpty() -> UsernameStatus.Empty
            usernameRegex.matches(username) -> UsernameStatus.Valid
            username.length < MIN_LENGTH -> UsernameStatus.TooShort(MIN_LENGTH - username.length)
            else -> UsernameStatus.InvalidCharacters
        }

    val canLogin: Boolean
        get() = status == UsernameStatus.Valid


    fun onUsernameChange(value: String) {
        username = value.take(MAX_LENGTH)
    }


    fun onRememberChange(value: Boolean) {
        remember = value
    }


    fun login(): Boolean {
        if (!canLogin) return false

        Session.login(username, remember)
        return true
    }
}