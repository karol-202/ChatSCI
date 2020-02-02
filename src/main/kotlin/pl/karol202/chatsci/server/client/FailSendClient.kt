package pl.karol202.chatsci.server.client

import pl.karol202.chatsci.model.ServerPacket

fun Client.thenClose() = FailSendClient(this)

class FailSendClient(private val delegate: Client) : SendClient
{
	override suspend fun send(packet: ServerPacket) = with(delegate) {
		send(packet)
		close()
	}
}
