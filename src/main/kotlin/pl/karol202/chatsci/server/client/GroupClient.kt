package pl.karol202.chatsci.server.client

class GroupClient(private val clients: List<SendClient>) : SendClient
{
	companion object
	{
		fun of(vararg clients: SendClient) = GroupClient(clients.toList())
	}

	override suspend fun send(packet: String) = clients.forEach { it.send(packet) }
}
