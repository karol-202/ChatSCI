package pl.karol202.chatsci.actor

import kotlinx.coroutines.CompletableDeferred

interface Actor<in A : Actor.Action<*>>
{
	open class Action<R>
	{
		private val deferred = CompletableDeferred<R>()

		fun complete(result: R) = deferred.complete(result)

		suspend fun await() = deferred.await()
	}

	suspend fun send(action: A)
}

suspend fun <A : Actor.Action<R>, R> Actor<A>.sendAwait(action: A) = action.also { send(it) }.await()
