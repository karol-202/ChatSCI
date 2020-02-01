package pl.karol202.chatsci.provider.logger

import org.slf4j.LoggerFactory
import pl.karol202.chatsci.util.Logger
import pl.karol202.chatsci.util.fromSLF4J
import kotlin.reflect.KClass

class SLF4JLoggerProvider : LoggerProvider
{
	override fun getLogger(clazz: KClass<*>) = Logger.fromSLF4J(LoggerFactory.getLogger(clazz.java))
}
