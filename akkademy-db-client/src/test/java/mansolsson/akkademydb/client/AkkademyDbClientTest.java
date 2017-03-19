package mansolsson.akkademydb.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

import mansolsson.akkademydb.model.KeyNotFoundException;

/**
 * Integration tests, requires a server instance to be running...
 */
public class AkkademyDbClientTest {
	private final AkkademyDbClient client = new AkkademyDbClient("127.0.0.1:2552");

	@Test
	public void shouldBeAbleToSetAndGetKeyValuePairs() throws InterruptedException, ExecutionException {
		this.client.set("my key", "my value");

		final Object value = ((CompletableFuture<Object>) this.client.get("my key")).get();

		Assert.assertEquals("my value", value);
	}

	@Test
	public void shouldBeNotBeAbleToGetDeletedValue() throws InterruptedException, ExecutionException {
		this.client.set("key to delete", "1");
		this.client.delete("key to delete");

		try {
			((CompletableFuture<Object>) this.client.get("key to delete")).get();
			Assert.fail();
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			Assert.assertEquals(KeyNotFoundException.class, cause.getClass());
		}
	}
}
