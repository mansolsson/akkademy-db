package mansolsson.akkademydb.server;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;
import mansolsson.akkademydb.model.DeleteRequest;
import mansolsson.akkademydb.model.GetRequest;
import mansolsson.akkademydb.model.KeyNotFoundException;
import mansolsson.akkademydb.model.SetRequest;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

public class AkkademyDbTest {
    private final TestActorRef<AkkademyDb> actorRef = TestActorRef.create(ActorSystem.create("akkademy"),
            Props.create(AkkademyDb.class));

    @Test
    public void whenSetRequestIsSentThenAddItToMap() throws Exception {
        this.actorRef.tell(new SetRequest("set-key", "set-value"), ActorRef.noSender());

        final Future<Object> future = Patterns.ask(this.actorRef, new GetRequest("set-key"), 1_000L);
        final CompletionStage<Object> completionStage = FutureConverters.toJava(future);
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>) completionStage;

        Assert.assertEquals("set-value", completableFuture.get(1, TimeUnit.SECONDS));
    }

    @Test
    public void whenGetRequestIsSentForUnknownKeyThenSendError() throws Exception {
        final Future<Object> future = Patterns.ask(this.actorRef, new GetRequest("unknown-key"), 1_000L);
        final CompletionStage<Object> completionStage = FutureConverters.toJava(future);
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>) completionStage;

        try {
            completableFuture.get(1, TimeUnit.SECONDS);
            Assert.fail();
        } catch (final ExecutionException e) {
            final Throwable exception = e.getCause();
            Assert.assertEquals(KeyNotFoundException.class, exception.getClass());
            Assert.assertEquals("unknown-key", ((KeyNotFoundException) exception).getKey());
        }
    }

    @Test
    public void whenDeleteRequestIsSentThenRemoveTheItemFromTheMap() throws Exception {
        this.actorRef.tell(new SetRequest("deleted-key", "deleted-value"), ActorRef.noSender());
        this.actorRef.tell(new DeleteRequest("deleted-key"), ActorRef.noSender());

        final Future<Object> future = Patterns.ask(this.actorRef, new GetRequest("deleted-key"), 1_000L);
        final CompletionStage<Object> completionStage = FutureConverters.toJava(future);
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>) completionStage;

        try {
            completableFuture.get(1, TimeUnit.SECONDS);
            Assert.fail();
        } catch (final ExecutionException e) {
            final Throwable exception = e.getCause();
            Assert.assertEquals(KeyNotFoundException.class, exception.getClass());
            Assert.assertEquals("deleted-key", ((KeyNotFoundException) exception).getKey());
        }
    }

    @Test
    public void whenUnknownObjectIsSentThenDoNothing() throws Exception {
        final Future<Object> future = Patterns.ask(this.actorRef, new Object(), 1_000L);
        final CompletionStage<Object> completionStage = FutureConverters.toJava(future);
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>) completionStage;

        try {
            completableFuture.get(1, TimeUnit.SECONDS);
            Assert.fail();
        } catch (final ExecutionException e) {
            final Throwable exception = e.getCause();
            Assert.assertEquals(ClassNotFoundException.class, exception.getClass());
        }
    }
}
