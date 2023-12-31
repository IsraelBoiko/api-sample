package com.ebanx

import com.ebanx.persistence.configurePersistence
import com.ebanx.plugins.*
import com.ebanx.router._root.registerRouters
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  configureHTTP()
  configureMonitoring()
  configureSerialization()
  configurePersistence()
  configureStatusPages()
  registerRouters()
}
