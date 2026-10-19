package dev.iln.cobblemongo.data.game

import dev.iln.cobblemongo.core.Paths
import dev.iln.cobblemongo.core.log.Logger
import dev.iln.cobblemongo.data.service.Architecture
import dev.iln.cobblemongo.data.service.Device
import dev.iln.cobblemongo.data.service.OperatingSystem
import fr.flowarg.azuljavadownloader.*
import fr.theshark34.openlauncherlib.JavaUtil
import java.nio.file.Path
import kotlin.io.path.*

class JavaChecker(private val javaVersion: Int) {

    private val javaPath = Paths.runtime.resolve("java-$javaVersion")

    private val javaArch = when (Device.arch) {
        Architecture.X64 -> AzulJavaArch.X64
        Architecture.Arm64 -> AzulJavaArch.AARCH64
    }

    private val javaOS = when (Device.os) {
        OperatingSystem.Windows -> AzulJavaOS.WINDOWS
        OperatingSystem.MacOS -> AzulJavaOS.MACOS
        else -> AzulJavaOS.LINUX
    }


    fun checkJava() {
        val isJavaValidate = useCurrentJava()

        if (!isJavaValidate) {
            if (javaPath.notExists() || javaPath.listDirectoryEntries().isEmpty()) downloadJava(javaPath)
            findAndSetJavaPath(javaPath)
        }
    }


    private fun downloadJava(javaPath: Path) {
        val javaDownloader = AzulJavaDownloader()
        val requestInfo = RequestedJavaInfo(javaVersion.toString(), AzulJavaType.JRE, javaOS, javaArch)

        runCatching {
            val buildInfo = javaDownloader.getBuildInfo(requestInfo)
            javaDownloader.downloadAndInstall(buildInfo, javaPath)
        }.onFailure {
            Logger.error("Error while downloading Java", it)
        }
    }


    @OptIn(ExperimentalPathApi::class)
    private fun findAndSetJavaPath(javaPath: Path) {
        javaPath
            .walk(PathWalkOption.INCLUDE_DIRECTORIES)
            .filter { it.isDirectory() && it.name.contains("zulu") }
            .first { javaDir ->
                if (javaDir.fileSize() == 0L) {
                    javaPath.deleteRecursively()
                    return checkJava()
                }

                return if (Device.os == OperatingSystem.MacOS) {
                    val jrePath = listOf(
                        javaDir.resolve("zulu-$javaVersion.jre").resolve("Contents").resolve("Home"),
                        javaDir.resolve("Contents").resolve("Home")
                    ).first { it.exists() }

                    val execPath = jrePath.resolve("bin").resolve("java").absolutePathString()

                    ProcessBuilder("chmod", "+x", execPath)
                        .redirectErrorStream(true)
                        .start()
                        .waitFor()

                    JavaUtil.setJavaCommand(execPath)
                } else {
                    JavaUtil.setJavaCommand(javaDir.resolve("bin").resolve("java").absolutePathString())
                }
            }
    }


    private fun useCurrentJava(): Boolean {
        val currentJavaVersion = System.getProperty("java.version")
        val javaHomePath = Path(System.getProperty("java.home"))
        val javaExecutable = javaHomePath.resolve("bin").resolve("java")

        if (!currentJavaVersion.startsWith(javaVersion.toString())) return false

        JavaUtil.setJavaCommand(javaExecutable.absolutePathString())
        return true
    }
}