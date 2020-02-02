package pl.karol202.chatsci.server

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import pl.karol202.chatsci.controller.ClientControllerFactory
import pl.karol202.chatsci.server.client.asClient
import pl.karol202.chatsci.util.*

private const val PORT = 16384

class SocketServerImpl(private val clientControllerFactory: ClientControllerFactory) : SocketServer
{
	private val logger = LoggerFactory.getLogger(this::class.java)

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
			logger.info("Client connected")
			socket.asClient().use { clientControllerFactory(it).run() }
			logger.info("Client disconnected")
		}
		catch(t: Throwable)
		{
			logger.warn("Unhandled exception in controller", t)
		}
	}
}
