package pl.karol202.chatsci.model

sealed class ClientPacket
{
	data class Nick(val username: String) : ClientPacket()
	{
		companion object
		{
			const val PREFIX = "NICK"
		}
	}

	data class Message(val recipient: String,
	                   val message: String) : ClientPacket()
	{
		companion object
		{
			const val PREFIX = "MSG"
		}
	}

	object Malformed : ClientPacket()
}

fun String.parseClientPacket(): ClientPacket
{
	val parts = split(';')
	return when(parts[0].toUpperCase())
	{
		ClientPacket.Nick.PREFIX -> parseNick(parts)
		ClientPacket.Message.PREFIX -> parseMessage(parts)
		else -> null
	} ?: ClientPacket.Malformed
}

private fun parseNick(parts: List<String>): ClientPacket.Nick?
{
	val nick = parts.getOrNull(1) ?: return null
	return ClientPacket.Nick(nick)
}

private fun parseMessage(parts: List<String>): ClientPacket.Message?
{
	val recipient = parts.getOrNull(1) ?: return null
	val message = parts.getOrNull(2) ?: return null
	return ClientPacket.Message(recipient, message)
}
