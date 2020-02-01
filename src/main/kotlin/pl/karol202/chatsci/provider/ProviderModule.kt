package pl.karol202.chatsci.provider

import org.koin.dsl.module
import pl.karol202.chatsci.provider.logger.LoggerProvider
import pl.karol202.chatsci.provider.logger.SLF4JLoggerProvider

fun providerModule() = module {
	single<LoggerProvider> { SLF4JLoggerProvider() }
}
