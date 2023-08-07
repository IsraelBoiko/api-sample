package com.ebanx.core

inline fun <T : Any, R> T?.opt(some: (T) -> R, none: () -> R): R =
  if (this === null) none() else some(this)