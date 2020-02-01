package pl.karol202.chatsci.dao

import org.koin.dsl.module
import pl.karol202.chatsci.dao.auth.UserDao
import pl.karol202.chatsci.dao.auth.UserDaoImpl

fun daoModule() = module {
	single<UserDao> { UserDaoImpl() }
}
