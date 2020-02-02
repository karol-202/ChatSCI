package pl.karol202.chatsci.dao.auth

import pl.karol202.chatsci.server.client.SendClient

interface UserDao
{
	val allUsers: Map<String, SendClient>

	fun insertUser(username: String, client: SendClient)

	fun deleteUser(username: String)

	fun getUserByName(username: String): SendClient?

	fun containsUser(username: String): Boolean
}
