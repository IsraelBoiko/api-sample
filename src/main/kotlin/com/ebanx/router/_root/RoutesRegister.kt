package com.ebanx.router._root

import com.ebanx.router.balance.registerBalanceRouters
import io.ktor.server.application.*

fun Application.registerRouters() {
  register()
}

fun Application.register() {
  registerResetRoute()
  registerBalanceRouters()
}