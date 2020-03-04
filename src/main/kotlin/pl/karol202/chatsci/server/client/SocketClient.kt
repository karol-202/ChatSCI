package pl.karol202.chatsci.server.client

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import kotlinx.coroutines.io.close
import kotlinx.coroutines.io.readUTF8Line
import org.slf4j.LoggerFactory
import pl.karol202.chatsci.model.ServerPacket
import pl.karol202.chatsci.model.parseClientPacket
import pl.karol202.chatsci.util.writeUTF8Line

fun Socket.asClient() = SocketClient(this)

class SocketClient(private val socket: Socket) : Client
{
	companion object
	{
		private val logger = LoggerFactory.getLogger(this::class.java)
	}

	private val inputChannel = socket.openReadChannel()
	private val outputChannel = socket.openWriteChannel(autoFlush = true)

	override suspend fun receive() = runCatching {
		val line = inputChannel.readUTF8Line() ?: return@runCatching null
		logger.debug("Received from ${socket.remoteAddress}: $line")
		line.parseClientPacket()
	}.getOrNull()

	override suspend fun send(packet: ServerPacket)
	{
		if(outputChannel.isClosedForWrite) return
		val line = packet.stringify()
		logger.debug("Sent to ${socket.remoteAddress}: $line")
		outputChannel.writeUTF8Line(line)
	}

	override fun close()
	{
		outputChannel.close()
		socket.close()
	}
}
