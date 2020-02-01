package pl.karol202.chatsci.server.client

import java.io.Closeable

interface Client : ReceiveClient, SendClient, Closeable

interface ReceiveClient
{
	suspend fun receive(): String?
}

interface SendClient
{
	suspend fun send(packet: String)
}
