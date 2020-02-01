package pl.karol202.chatsci.server

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import pl.karol202.chatsci.controller.ClientControllerFactory
import pl.karol202.chatsci.provider.logger.LoggerProvider
import pl.karol202.chatsci.provider.logger.logger
import pl.karol202.chatsci.server.client.asClient
import pl.karol202.chatsci.util.info
import pl.karol202.chatsci.util.trace
import pl.karol202.chatsci.util.warn

private const val PORT = 16384

class SocketServerImpl(loggerProvider: LoggerProvider,
					   private val clientControllerFactory: ClientControllerFactory) : SocketServer,
                                                                                       LoggerProvider by loggerProvider
{
	private val logger by logger()

	override suspend fun run()
	{
		logger.info("Starting socket server")
		acceptClients(createServerSocket())
	}

	private fun createServerSocket() = aSocket(createActorSelectorManager()).tcp().bind(port = PORT)

	private fun createActorSelectorManager() = ActorSelectorManager(Dispatchers.IO)

	private suspend fun acceptClients(serverSocket: ServerSocket) = coroutineScope {
		while(true) handleClient(serverSocket.accept())
	}

	private fun CoroutineScope.handleClient(socket: Socket) = launch {
		try
		{
			logger.trace("Client connected")
			socket.asClient().use { clientControllerFactory(it).run() }
			logger.trace("Client disconnected")
		}
		catch(t: Throwable)
		{
			logger.warn("Unhandled exception in controller", t)
		}
	}
}
