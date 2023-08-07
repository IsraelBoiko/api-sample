package com.ebanx.service

import com.ebanx.persistence.*

class ResetService {
  companion object : ResetServiceTrait
}

interface ResetServiceTrait {
  suspend fun execute() = transaction {
    Account.all().forEach {
      it.delete()
    }
  }
}