package com.ebanx.service

import com.ebanx.core.*
import com.ebanx.persistence.*
import kotlinx.serialization.*

@Serializable
enum class EventType {
  @SerialName("deposit")
  Deposit,
  @SerialName("withdraw")
  Withdraw,
}

@Serializable
data class EventModel(val type: EventType, val origin: String? = null, val destination: String? = null, val amount: Long)

class EventService {
  companion object : EventServiceTrait
}

interface EventServiceTrait {
  suspend fun register(model: EventModel) = run {
    when (model.type) {
      EventType.Deposit ->
        deposit(model.destination ?: throwBadRequest(), model.amount)
      
      EventType.Withdraw ->
        throwNotFound()
    }
  }
  
  private suspend fun deposit(destination: String, amount: Long) = transaction {
    mapOf(
      "destination" to Account.findById(destination).opt({
        it.balance += amount
        
        it
      }, {
        Account.new(destination) {
          balance = amount
        }
      }).toModel()
    )
  }
}