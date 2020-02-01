package pl.karol202.chatsci.provider.logger

import pl.karol202.chatsci.util.Logger
import kotlin.reflect.KClass

interface LoggerProvider
{
	fun getLogger(clazz: KClass<*>): Logger
}

inline fun <reified P : LoggerProvider> P.logger() = lazy { getLogger(P::class) }
