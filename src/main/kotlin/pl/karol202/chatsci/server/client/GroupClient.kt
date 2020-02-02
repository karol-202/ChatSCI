package pl.karol202.chatsci.server.client

import pl.karol202.chatsci.model.ServerPacket

class GroupClient(private val clients: List<SendClient>) : SendClient
{
	companion object
	{
		fun of(vararg clients: SendClient) = GroupClient(clients.toList())
	}

	override suspend fun send(packet: ServerPacket) = clients.forEach { it.send(packet) }
}
