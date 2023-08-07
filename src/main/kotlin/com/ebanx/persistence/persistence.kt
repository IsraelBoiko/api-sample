package com.ebanx.persistence

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun configurePersistence() {
  Database.connect(
    url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
    user = "root",
    driver = "org.h2.Driver",
    password = ""
  )
  
  transaction {
    addLogger(StdOutSqlLogger)
    
    SchemaUtils.create(Account.Table)
  }
}

suspend fun <R> transaction(body: () -> R): R = newSuspendedTransaction(Dispatchers.IO) {
  body()
}