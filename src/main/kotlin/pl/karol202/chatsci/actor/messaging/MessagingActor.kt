package pl.karol202.chatsci.actor.messaging

interface MessagingActor
{
    sealed class Action
    {
        data class Login(val username: String) : Action()

        data class Logout(val username: String) : Action()

        data class Send(val sender: String,
                        val recipients: List<String>,
                        val message: String) : Action()
    }

    suspend fun send(action: Action)
}
