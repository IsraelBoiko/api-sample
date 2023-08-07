package com.ebanx.router.balance

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerBalanceRouters() {
  routing {
    route("/balance") {
      get {
        call.respond(HttpStatusCode.NotFound, 0)
      }
    }
  }
}