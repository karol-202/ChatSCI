package pl.karol202.chatsci.dao.auth

import pl.karol202.chatsci.server.client.SendClient

class UserDaoImpl : UserDao
{
	private val users = mutableMapOf<String, SendClient>()

	override val allUsers: Map<String, SendClient> = users

	override fun insertUser(username: String, client: SendClient) =
			(!containsClient(username)).also { if(it) users += username to client }

	override fun deleteUser(username: String) =
			containsClient(username).also { if(it) users -= username }

	override fun getUserByName(username: String) = users[username]

	private fun containsClient(userId: String) = users.containsKey(userId)
}
