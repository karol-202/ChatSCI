package pl.karol202.chatsci.actor.messaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import pl.karol202.chatsci.dao.auth.UserDao
import pl.karol202.chatsci.model.ServerPacket
import pl.karol202.chatsci.server.client.SendClient

class MessagingActorImpl(private val userDao: UserDao) : MessagingActor
{
    private val coroutineScope = CoroutineScope(context = GlobalScope.coroutineContext)
    private val actor = coroutineScope.actor<MessagingActor.Action<*>>(capacity = Channel.BUFFERED) {
        for(action in this) onAction(action)
    }

    private suspend fun onAction(action: MessagingActor.Action<*>) = when(action)
    {
        is MessagingActor.Action.Login -> action.complete(login(action.username, action.client))
        is MessagingActor.Action.Logout -> action.complete(logout(action.username))
        is MessagingActor.Action.Send -> action.complete(send(action.sender, action.recipient, action.message))
    }

    private suspend fun login(username: String, client: SendClient): String?
    {
        if(userDao.containsUser(username)) return client.send(ServerPacket.Error(ServerPacket.Error.USERNAME_BUSY)).let { null }
        userDao.allUsers.values.forEach { it.send(ServerPacket.Join(username)) }
        userDao.insertUser(username, client)
        return username
    }

    private suspend fun logout(username: String)
    {
        if(!userDao.containsUser(username)) return
        userDao.deleteUser(username)
        userDao.allUsers.values.forEach { it.send(ServerPacket.Leave(username)) }
    }

    private suspend fun send(sender: String, recipient: String, message: String)
    {
        if(!userDao.containsUser(sender)) return
        val recipientClient = userDao.getUserByName(recipient) ?: return
        recipientClient.send(ServerPacket.Message(sender, message))
    }

    override suspend fun send(action: MessagingActor.Action<*>) = actor.send(action)
}
