package com.tiredbones.logger

object Logger {
  private val loggers = mutableListOf<LoggerDefinition>()

  @Synchronized
  fun addLogger(logger: LoggerDefinition) {
    loggers.add(logger)
  }

  // Verbose
  @JvmStatic
  fun v(tag: String, message: String) {
    loggers.forEach { it.v(tag, message, null) }
  }

  @JvmStatic
  fun v(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.v(tag, message, t) }
  }

  // Info
  @JvmStatic
  fun i(tag: String, message: String) {
    loggers.forEach { it.i(tag, message, null) }
  }

  @JvmStatic
  fun i(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.i(tag, message, t) }
  }

  // Debug
  @JvmStatic
  fun d(tag: String, message: String) {
    loggers.forEach { it.d(tag, message, null) }
  }

  @JvmStatic
  fun d(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.d(tag, message, t) }
  }

  // Warning
  @JvmStatic
  fun w(tag: String, message: String) {
    loggers.forEach { it.w(tag, message, null) }
  }

  @JvmStatic
  fun w(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.w(tag, message, t) }
  }

  // Error
  @JvmStatic
  fun e(tag: String, message: String) {
    loggers.forEach { it.e(tag, message, null) }
  }

  @JvmStatic
  fun e(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.e(tag, message, t) }
  }

  // Assertion
  @JvmStatic
  fun wtf(tag: String, message: String) {
    loggers.forEach { it.wtf(tag, message, null) }
  }


  @JvmStatic
  fun wtf(tag: String, t: Throwable, message: String? = null) {
    loggers.forEach { it.wtf(tag, message, t) }
  }
}
