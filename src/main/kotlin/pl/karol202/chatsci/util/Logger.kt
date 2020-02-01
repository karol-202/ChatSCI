package pl.karol202.chatsci.util

import org.slf4j.event.Level

interface Logger
{
	companion object

	fun log(level: Level, message: String, throwable: Throwable? = null)
}

fun Logger.error(message: String, throwable: Throwable? = null) = log(Level.ERROR, message, throwable)
fun Logger.warn(message: String, throwable: Throwable? = null) = log(Level.WARN, message, throwable)
fun Logger.info(message: String, throwable: Throwable? = null) = log(Level.INFO, message, throwable)
fun Logger.debug(message: String, throwable: Throwable? = null) = log(Level.DEBUG, message, throwable)
fun Logger.trace(message: String, throwable: Throwable? = null) = log(Level.TRACE, message, throwable)

fun Logger.Companion.fromSLF4J(logger: org.slf4j.Logger) = object : Logger {

	override fun log(level: Level, message: String, throwable: Throwable?) = when(level)
	{
		Level.ERROR ->
			if(throwable != null) logger.error(message, throwable)
			else logger.error(message)
		Level.WARN ->
			if(throwable != null) logger.warn(message, throwable)
			else logger.warn(message)
		Level.INFO ->
			if(throwable != null) logger.info(message, throwable)
			else logger.info(message)
		Level.DEBUG ->
			if(throwable != null) logger.debug(message, throwable)
			else logger.debug(message)
		Level.TRACE ->
			if(throwable != null) logger.trace(message, throwable)
			else logger.trace(message)
	}
}
