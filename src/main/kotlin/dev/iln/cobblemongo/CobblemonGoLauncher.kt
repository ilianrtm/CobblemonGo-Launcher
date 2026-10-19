package dev.iln.cobblemongo

import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.core.log.Notifier
import dev.iln.cobblemongo.data.config.Language
import dev.iln.cobblemongo.data.service.Device
import dev.iln.cobblemongo.resources.Res
import dev.iln.cobblemongo.resources.notif_unexpected_error_message
import dev.iln.cobblemongo.resources.notif_unexpected_error_title
import dev.iln.cobblemongo.ui.window.AppWindow

fun main() {
    Logger.init()
    Language.apply()

    Logger.info("System: ${Device.os} ${Device.arch}, ${Device.totalMemoryGb ?: "unknown"}Gb RAM")

    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        Logger.error("Uncaught exception in thread ${Thread.currentThread().name}", e)
        Notifier.error(Res.string.notif_unexpected_error_title, Res.string.notif_unexpected_error_message)
    }

    AppWindow().createWindow()
}