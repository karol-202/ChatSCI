package pl.karol202.chatsci.server.client

import pl.karol202.chatsci.model.ClientPacket
import pl.karol202.chatsci.model.ServerPacket
import java.io.Closeable

interface Client : ReceiveClient, SendClient, Closeable

interface ReceiveClient
{
	suspend fun receive(): ClientPacket?
}

interface SendClient
{
	suspend fun send(packet: ServerPacket)
}
