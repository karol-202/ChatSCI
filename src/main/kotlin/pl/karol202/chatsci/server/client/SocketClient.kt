package pl.karol202.chatsci.server.client

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import kotlinx.coroutines.io.close
import kotlinx.coroutines.io.readUTF8Line
import pl.karol202.chatsci.util.writeUTF8Line

fun Socket.asClient() = SocketClient(this)

class SocketClient(private val socket: Socket) : Client
{
	private val inputChannel = socket.openReadChannel()
	private val outputChannel = socket.openWriteChannel(autoFlush = true)

	override suspend fun receive() = runCatching {
		inputChannel.readUTF8Line()
	}.getOrNull()

	override suspend fun send(packet: String) =
			outputChannel.takeUnless { it.isClosedForWrite }?.writeUTF8Line(packet) ?: Unit

	override fun close()
	{
		outputChannel.close()
		socket.close()
	}
}
