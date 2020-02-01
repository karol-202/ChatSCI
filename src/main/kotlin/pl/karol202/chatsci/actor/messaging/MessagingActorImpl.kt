package pl.karol202.chatsci.actor.messaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import pl.karol202.chatsci.dao.auth.UserDao

class MessagingActorImpl(private val userDao: UserDao) : MessagingActor
{
    private val coroutineScope = CoroutineScope(context = GlobalScope.coroutineContext)
    private val actor = coroutineScope.actor<MessagingActor.Action>(capacity = Channel.BUFFERED) {
        for(action in this) onAction(action)
    }

    private fun onAction(action: MessagingActor.Action) = when(action)
    {
        is MessagingActor.Action.Login -> login(action.username)
        is MessagingActor.Action.Logout -> logout(action.username)
        is MessagingActor.Action.Send -> send(action.sender, action.recipients, action.message)
    }

    private fun login(username: String)
    {
        userDao.insertUser(username)
    }

    private fun logout(username: String)
    {
        userDao.deleteUser(username)
    }

    private fun send(sender: String, recipients: List<String>, message: String)
    {

    }

    override suspend fun send(action: MessagingActor.Action) = actor.send(action)
}
