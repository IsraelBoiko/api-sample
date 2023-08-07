package com.ebanx.service

import com.ebanx.core.throwNotFound
import com.ebanx.persistence.*

class BalanceService {
  companion object : BalanceServiceTrait
}

interface BalanceServiceTrait {
  suspend fun get(accountId: String) = transaction {
    val account = Account.findById(accountId) ?: throwNotFound()
    
    account.balance
  }
}