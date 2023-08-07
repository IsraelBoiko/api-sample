package com.ebanx.router._root

import io.ktor.server.application.*

fun Application.registerRouters() {
  register()
}

fun Application.register() {
  registerResetRoute()
}