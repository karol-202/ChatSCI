package pl.karol202.chatsci

import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import pl.karol202.chatsci.util.EmptyKoinComponent
import pl.karol202.chatsci.server.SocketServer
import pl.karol202.chatsci.server.serverModule

suspend fun main()
{
    startKoin()
    EmptyKoinComponent.runSocketServer()
}

private fun startKoin() = startKoin {
    modules(listOf(serverModule()))
}

private suspend fun KoinComponent.runSocketServer() = get<SocketServer>().run()
