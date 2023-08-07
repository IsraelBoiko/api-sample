package com.ebanx.router.balance

import com.ebanx.core.throwBadRequest
import com.ebanx.service.BalanceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerBalanceRouters() {
  routing {
    route("/balance") {
      get {
        call.respond(HttpStatusCode.OK, BalanceService.get(call.parameters["account_id"] ?: throwBadRequest()))
      }
    }
  }
}