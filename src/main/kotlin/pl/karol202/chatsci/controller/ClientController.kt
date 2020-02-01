package pl.karol202.chatsci.controller

import pl.karol202.chatsci.server.client.Client

interface ClientController
{
	suspend fun run()
}

typealias ClientControllerFactory = (Client) -> ClientController
