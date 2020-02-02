package pl.karol202.chatsci.actor.messaging

import pl.karol202.chatsci.actor.Actor
import pl.karol202.chatsci.server.client.SendClient

interface MessagingActor : Actor<MessagingActor.Action<*>>
{
    sealed class Action<R> : Actor.Action<R>()
    {
        data class Login(val username: String,
                         val client: SendClient) : Action<String?>()

        data class Logout(val username: String) : Action<Unit>()

        data class Send(val sender: String,
                        val recipient: String,
                        val message: String) : Action<Unit>()
    }
}
