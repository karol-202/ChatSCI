package pl.karol202.chatsci.dao.auth

import pl.karol202.chatsci.server.client.SendClient

interface UserDao
{
	val allUsers: Map<String, SendClient>

	fun insertUser(username: String, client: SendClient): Boolean

	fun deleteUser(username: String): Boolean

	fun getUserByName(username: String): SendClient?
}
