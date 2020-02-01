package pl.karol202.chatsci.controller

import pl.karol202.chatsci.server.client.Client
import pl.karol202.chatsci.server.client.thenClose

private const val LOGIN_PREFIX = "NICK;"

class ClientControllerImpl(private val client: Client) : ClientController
{
	override suspend fun run()
	{
		val username = extractUsername(client.receive()) ?: return client.thenClose().send("You have to log in first")
		// Login
		runLogged(username)
	}

	private fun extractUsername(packet: String?) = when
	{
		packet == null -> null
		packet.startsWith(LOGIN_PREFIX, ignoreCase = true) -> packet.substringAfter(LOGIN_PREFIX)
		else -> null
	}

	private suspend fun runLogged(user: String) =
			try { handlePackets(user) }
			finally { ensureLoggedOut(user) }

	private suspend fun handlePackets(user: String)
	{
		while(true) handlePacket(user, client.receive() ?: break)
	}

	private suspend fun handlePacket(user: String, packet: String) = Unit

	private suspend fun ensureLoggedOut(user: String)
	{

	}
}
