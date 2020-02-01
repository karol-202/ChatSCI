package pl.karol202.chatsci.provider.client

import pl.karol202.chatsci.server.client.SendClient

interface ClientProvider
{
	suspend fun requireClient(userId: String): SendClient
}
