package ua.nedz.demo

import io.grpc.Server
import io.grpc.ServerBuilder

fun main() {
    val server = PlanetServer()
    server.start()
    server.blockUntilShutdown()
}

class PlanetServer (private val port: Int = 50061, private val serverBuilder: ServerBuilder<*> = ServerBuilder.forPort(port)) {
    private lateinit var server: Server

    fun start() {
        server = serverBuilder
                .addService(PlanetServiceImpl())
                .build()
                .start()
        println("Server started!")
    }

    /**
     * Await termination on the ua.nedz.demo.main thread since the grpc library uses daemon threads.
     */
    @Throws(InterruptedException::class)
    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    fun stop() {
        if (::server.isInitialized)
            server.shutdown()
    }
}