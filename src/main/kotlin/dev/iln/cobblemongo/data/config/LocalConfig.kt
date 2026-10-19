package dev.iln.cobblemongo.data.config

import dev.iln.cobblemongo.data.service.Device
import kotlinx.serialization.Serializable

enum class AfterLaunchAction { Keep, Minimize, Close }


@Serializable
data class LocalConfig(
    val username: String? = null,
    val ram: Int = Device.recommendedMemoryGb,
    val window: Window = Window(),
    val autoJoin: Boolean = true,
    val afterLaunch: AfterLaunchAction = AfterLaunchAction.Minimize,
    val language: Language = Language.Default,
) {
    companion object {
        const val MIN_MEMORY_GB = 2
        const val MAX_MEMORY_GB = 16
    }


    fun coercedToLimits(): LocalConfig = copy(
        ram = ram.coerceIn(MIN_MEMORY_GB, Device.maxMemoryGb),
        window = window.coercedToLimits()
    )
}


@Serializable
data class Window(
    val width: Int = 1280,
    val height: Int = 720,
    val fullscreen: Boolean = false,
) {
    companion object {
        const val MIN_WIDTH = 854
        const val MIN_HEIGHT = 480
    }


    fun coercedToLimits(): Window = copy(
        width = width.coerceAtLeast(MIN_WIDTH),
        height = height.coerceAtLeast(MIN_HEIGHT)
    )
}