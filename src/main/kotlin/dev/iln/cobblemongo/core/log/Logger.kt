package dev.iln.cobblemongo.core.log

import dev.iln.cobblemongo.core.Paths
import java.nio.file.StandardOpenOption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.writeText

object Logger {

    private val file = Paths.logs.resolve("launcher.log")
    private val timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")


    fun init() {
        runCatching {
            Paths.logs.createDirectories()
            file.deleteIfExists()
        }
    }


    fun info(message: String) {
        write("INFO", message)
    }


    fun error(message: String, cause: Throwable? = null) {
        write("ERROR", if (cause != null) "$message\n${cause.stackTraceToString()}" else message)
    }


    @Synchronized
    private fun write(level: String, message: String) {
        val line = "[${LocalDateTime.now().format(timeFormat)}] [$level] $message\n"

        print(line)
        runCatching { file.writeText(line, Charsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND) }
    }
}