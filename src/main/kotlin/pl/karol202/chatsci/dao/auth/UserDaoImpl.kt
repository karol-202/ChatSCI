package pl.karol202.chatsci.dao.auth

import pl.karol202.chatsci.server.client.SendClient

class UserDaoImpl : UserDao
{
	private val users = mutableMapOf<String, SendClient>()

	override val allUsers: Map<String, SendClient> = users

	override fun insertUser(username: String, client: SendClient)
	{
		users += username to client
	}

	override fun deleteUser(username: String)
	{
		users -= username
	}

	override fun getUserByName(username: String) = users[username]

	override fun containsUser(username: String) = users.containsKey(username)
}
