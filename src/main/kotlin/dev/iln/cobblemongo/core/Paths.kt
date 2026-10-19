package dev.iln.cobblemongo.core

import dev.nucleusframework.core.runtime.NucleusApp
import dev.iln.cobblemongo.data.service.Device
import dev.iln.cobblemongo.data.service.OperatingSystem
import java.nio.file.Path
import kotlin.io.path.Path

object Paths {

    private val APP_NAME: String
        get() = NucleusApp.appName ?: "CobblemonGo"


    val root: Path by lazy {
        val userHome = System.getProperty("user.home")

        val path = when (Device.os) {
            OperatingSystem.Windows -> Path(System.getenv("APPDATA") ?: userHome, ".$APP_NAME")

            OperatingSystem.MacOS -> Path(userHome, "Library", "Application Support", APP_NAME)

            OperatingSystem.Linux -> Path(userHome, ".$APP_NAME")
        }

        path.toAbsolutePath()
    }

    val config: Path get() = root.resolve("config.json")
    val runtime: Path get() = root.resolve("runtime")
    val logs: Path get() = root.resolve("logs")
}