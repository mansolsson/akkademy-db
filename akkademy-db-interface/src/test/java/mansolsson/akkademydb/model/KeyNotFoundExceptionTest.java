package mansolsson.akkademydb.model;

import org.junit.Assert;
import org.junit.Test;

public class KeyNotFoundExceptionTest {
	@Test
	public void constructorTakingKeyParamShouldSetKey() {
		final String key = "key-that-does-not-exist";

		final KeyNotFoundException exception = new KeyNotFoundException(key);

		Assert.assertEquals(key, exception.getKey());
	}
}
