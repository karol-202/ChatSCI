package pl.karol202.chatsci.controller

import org.koin.dsl.module

fun controllerModule() = module {
	single<ClientControllerFactory> { { client -> ClientControllerImpl(client, get()) } }
}
