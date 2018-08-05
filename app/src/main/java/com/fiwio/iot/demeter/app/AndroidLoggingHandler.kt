package com.fiwio.iot.demeter.app

import android.util.Log
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.LogRecord


/**
 * Make JUL work on Android.
 */
class AndroidLoggingHandler : Handler() {

    override fun close() {}

    override fun flush() {}

    override fun publish(record: LogRecord) {
        if (!super.isLoggable(record))
            return

        val name = record.getLoggerName()
        val maxLength = 30
        val tag = if (name.length > maxLength) name.substring(name.length - maxLength) else name

        try {
            val level = getAndroidLevel(record.getLevel())
            Log.println(level, tag, record.getMessage())
            if (record.getThrown() != null) {
                Log.println(level, tag, Log.getStackTraceString(record.getThrown()))
            }
        } catch (e: RuntimeException) {
            Log.e("AndroidLoggingHandler", "Error logging message.", e)
        }

    }

    companion object {

        fun reset(rootHandler: Handler) {
            val rootLogger = LogManager.getLogManager().getLogger("")
            val handlers = rootLogger.getHandlers()
            for (handler in handlers) {
                rootLogger.removeHandler(handler)
            }
            rootLogger.addHandler(rootHandler)
        }

        internal fun getAndroidLevel(level: Level): Int {
            val value = level.intValue()
            return if (value >= 1000) {
                Log.ERROR
            } else if (value >= 900) {
                Log.WARN
            } else if (value >= 800) {
                Log.INFO
            } else {
                Log.DEBUG
            }
        }
    }
}