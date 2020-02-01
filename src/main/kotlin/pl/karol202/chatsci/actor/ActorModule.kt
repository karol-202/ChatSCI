package pl.karol202.chatsci.actor

import org.koin.dsl.module
import pl.karol202.chatsci.actor.messaging.MessagingActor
import pl.karol202.chatsci.actor.messaging.MessagingActorImpl

fun actorModule() = module {
	single<MessagingActor> { MessagingActorImpl(get()) }
}
