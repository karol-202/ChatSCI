package pl.karol202.chatsci.server.client

fun Client.thenClose() = FailSendClient(this)

class FailSendClient(private val delegate: Client) : SendClient
{
	override suspend fun send(packet: String) = with(delegate) {
		send(packet)
		close()
	}
}
