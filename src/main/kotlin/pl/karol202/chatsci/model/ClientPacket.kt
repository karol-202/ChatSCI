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

fun String.parseClientPacket() = extractType(this)?.let { (type, rest) ->
	when(type.toUpperCase())
	{
		ClientPacket.Nick.PREFIX -> parseNick(rest)
		ClientPacket.Message.PREFIX -> parseMessage(rest)
		else -> null
	}
} ?: ClientPacket.Malformed

private fun extractType(packet: String) = packet.splitOnFirst(';')

private fun parseNick(rest: String) = ClientPacket.Nick(rest)

private fun parseMessage(rest: String) =
		rest.splitOnFirst(';')?.let { (recipient, message) -> ClientPacket.Message(recipient, message) }

private fun String.splitOnFirst(delimiter: Char) =
		split(delimiter, limit = 2).takeIf { it.size == 2 }?.let { (first, second) -> first to second }
