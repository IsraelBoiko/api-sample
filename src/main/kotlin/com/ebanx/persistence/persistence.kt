package com.ebanx.persistence

import org.jetbrains.exposed.sql.Database

fun configurePersistence() {
  Database.connect(
    url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
    user = "root",
    driver = "org.h2.Driver",
    password = ""
  )
}