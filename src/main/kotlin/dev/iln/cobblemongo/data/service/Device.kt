package dev.iln.cobblemongo.data.service

import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.data.config.LocalConfig
import dev.nucleusframework.systeminfo.SystemInfo
import kotlin.math.roundToInt

enum class OperatingSystem { Windows, MacOS, Linux }

enum class Architecture { X64, Arm64 }


object Device {

    private const val RESERVED_FOR_SYSTEM_GB = 2
    private const val FALLBACK_RECOMMENDED_GB = 4
    private const val MAX_RECOMMENDED_GB = 8

    private val osInfo = runCatching { SystemInfo.osInfo() }
        .onFailure { Logger.error("Failed to read system info", it) }
        .getOrNull()


    val os = (osInfo?.name ?: System.getProperty("os.name")).lowercase().let {
        when {
            "win" in it -> OperatingSystem.Windows
            "mac" in it || "darwin" in it -> OperatingSystem.MacOS
            else -> OperatingSystem.Linux
        }
    }

    val arch = (osInfo?.cpuArch ?: System.getProperty("os.arch")).lowercase().let {
        if ("arm" in it || "aarch64" in it) Architecture.Arm64 else Architecture.X64
    }


    val totalMemoryGb = runCatching { SystemInfo.memoryInfo()?.totalMemory }
        .onFailure { Logger.error("Failed to read system memory", it) }
        .getOrNull()
        ?.let { (it / (1024.0 * 1024.0 * 1024.0)).roundToInt() }

    val maxMemoryGb = totalMemoryGb
        ?.let { (it - RESERVED_FOR_SYSTEM_GB).coerceIn(LocalConfig.MIN_MEMORY_GB, LocalConfig.MAX_MEMORY_GB) }
        ?: LocalConfig.MAX_MEMORY_GB

    val recommendedMemoryGb = (totalMemoryGb?.div(2) ?: FALLBACK_RECOMMENDED_GB)
        .coerceIn(FALLBACK_RECOMMENDED_GB, MAX_RECOMMENDED_GB)
        .coerceAtMost(maxMemoryGb)
}