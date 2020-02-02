package pl.karol202.chatsci.server

import org.koin.dsl.module

fun serverModule() = module {
	single<SocketServer> { SocketServerImpl(get()) }
}
