package dev.iln.cobblemongo.data.service

import dev.nucleusframework.core.runtime.NucleusApp
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.time.Duration
import java.util.concurrent.CompletableFuture

object WebService {

    private val TIMEOUT = Duration.ofSeconds(10)

    private val client = HttpClient.newBuilder()
        .connectTimeout(TIMEOUT)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build()


    fun <T> request(url: String, handler: BodyHandler<T>): HttpResponse<T> =
        client.send(buildRequest(url), handler)


    fun <T> requestAsync(url: String, handler: BodyHandler<T>): CompletableFuture<HttpResponse<T>> =
        client.sendAsync(buildRequest(url), handler)


    private fun buildRequest(url: String): HttpRequest = HttpRequest.newBuilder(URI.create(url))
        .timeout(TIMEOUT)
        .header("User-Agent", "CobblemonGo-Launcher/${NucleusApp.version ?: "dev"}")
        .build()
}