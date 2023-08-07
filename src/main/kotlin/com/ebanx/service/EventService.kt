package com.ebanx.service

import com.ebanx.core.opt
import com.ebanx.persistence.*
import kotlinx.serialization.*

@Serializable
enum class EventType {
  @SerialName("deposit")
  Deposit,
}

@Serializable
data class EventModel(val type: EventType, val destination: String, val amount: Long)

class EventService {
  companion object : EventServiceTrait
}

interface EventServiceTrait {
  suspend fun register(model: EventModel) = run {
    when (model.type) {
      EventType.Deposit ->
        deposit(model.destination, model.amount)
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