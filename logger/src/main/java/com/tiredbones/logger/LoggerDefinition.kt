package com.tiredbones.logger

interface LoggerDefinition {
  fun v(tag: String, message: String?, throwable: Throwable?)

  fun i(tag: String, message: String?, throwable: Throwable?)

  fun d(tag: String, message: String?, throwable: Throwable?)

  fun w(tag: String, message: String?, throwable: Throwable?)

  fun e(tag: String, message: String?, throwable: Throwable?)

  fun wtf(tag: String, message: String?, throwable: Throwable?)
}
