package pl.karol202.chatsci.controller

import pl.karol202.chatsci.actor.messaging.MessagingActor
import pl.karol202.chatsci.model.ClientPacket
import pl.karol202.chatsci.server.client.Client
import pl.karol202.chatsci.server.client.thenClose
import pl.karol202.chatsci.actor.sendAwait
import pl.karol202.chatsci.model.ServerPacket

class ClientControllerImpl(private val client: Client,
                           private val messagingActor: MessagingActor) : ClientController
{
	override suspend fun run()
	{
		val username = auth(client.receive()) ?: return
		runLogged(username)
	}

	private suspend fun auth(packet: ClientPacket?): String? = when(packet)
	{
		null -> null
		is ClientPacket.Nick ->
			messagingActor.sendAwait(MessagingActor.Action.Login(packet.username, client)) ?: auth(client.receive())
		is ClientPacket.Malformed -> {
			client.send(ServerPacket.Error(ServerPacket.Error.MALFORMED_PACKET))
			auth(client.receive())
		}
		else -> {
			client.send(ServerPacket.Error(ServerPacket.Error.NOT_LOGGED))
			auth(client.receive())
		}
	}

	private suspend fun runLogged(user: String) =
			try { handlePackets(user) }
			finally { ensureLoggedOut(user) }

	private suspend fun handlePackets(user: String)
	{
		while(true) handlePacket(user, client.receive() ?: break)
	}

	private suspend fun handlePacket(user: String, packet: ClientPacket) = when(packet)
	{
		is ClientPacket.Nick -> client.send(ServerPacket.Error(ServerPacket.Error.ALREADY_LOGGED))
		is ClientPacket.Message -> messagingActor.send(MessagingActor.Action.Send(user, packet.recipient, packet.message))
		is ClientPacket.Malformed -> client.send(ServerPacket.Error(ServerPacket.Error.MALFORMED_PACKET))
	}

	private suspend fun ensureLoggedOut(username: String) = messagingActor.send(MessagingActor.Action.Logout(username))
}
