package mansolsson.akkademydb.client;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import mansolsson.akkademydb.model.DeleteRequest;
import mansolsson.akkademydb.model.GetRequest;
import mansolsson.akkademydb.model.SetRequest;
import scala.compat.java8.FutureConverters;

public class AkkademyDbClient {
	private final ActorSystem system = ActorSystem.create("LocalSystem");
	private final ActorSelection remoteDb;

	public AkkademyDbClient(final String remoteAddress) {
		this.remoteDb = this.system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db");
	}

	public CompletionStage<Object> set(final String key, final Object value) {
		return FutureConverters.toJava(Patterns.ask(this.remoteDb, new SetRequest(key, value), 2_000L));
	}

	public CompletionStage<Object> get(final String key) {
		return FutureConverters.toJava(Patterns.ask(this.remoteDb, new GetRequest(key), 2_000L));
	}

	public CompletionStage<Object> delete(final String key) {
		return FutureConverters.toJava(Patterns.ask(this.remoteDb, new DeleteRequest(key), 2_000L));
	}
}
