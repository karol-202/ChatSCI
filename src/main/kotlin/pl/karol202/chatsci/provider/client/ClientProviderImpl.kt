package pl.karol202.chatsci.provider.client

import pl.karol202.chatsci.dao.auth.UserDao

class ClientProviderImpl(private val userDao: UserDao) : ClientProvider
{
	override suspend fun requireClient(userId: String) =
			userDao.getUserByName(userId) ?: throw IllegalStateException("Client not found")
}
