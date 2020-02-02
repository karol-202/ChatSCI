package pl.karol202.chatsci.model

sealed class ServerPacket
{
	data class Join(val username: String) : ServerPacket()
	{
		companion object
		{
			private const val PREFIX = "JOIN"
		}

		override fun stringify() = "$PREFIX;$username"
	}

	data class Leave(val username: String) : ServerPacket()
	{
		companion object
		{
			private const val PREFIX = "LEAVE"
		}

		override fun stringify() = "$PREFIX;$username"
	}

	data class Message(val sender: String,
	                   val message: String) : ServerPacket()
	{
		companion object
		{
			private const val PREFIX = "MSG"
		}

		override fun stringify() = "$PREFIX;$sender;$message"
	}

	data class Error(val message: String) : ServerPacket()
	{
		companion object
		{
			private const val PREFIX = "ERROR"

			const val NOT_LOGGED = "You have to log in first"
			const val ALREADY_LOGGED = "You are already logged"
			const val USERNAME_BUSY = "Someone has already chosen this nick"
			const val MALFORMED_PACKET = "I don't understand your packet"
		}

		override fun stringify() = "$PREFIX;$message"
	}

	abstract fun stringify(): String
}
