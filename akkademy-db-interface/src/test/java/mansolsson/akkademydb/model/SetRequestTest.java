package mansolsson.akkademydb.model;

import org.junit.Assert;
import org.junit.Test;

public class SetRequestTest {
	@Test
	public void constructorTakingKeyParamShouldSetKey() {
		final String key = "set-key";

		final SetRequest request = new SetRequest(key, "set-value");

		Assert.assertEquals(key, request.getKey());
	}

	@Test
	public void constructorTakingValueParamShouldSetValue() {
		final Object value = "set-value";

		final SetRequest request = new SetRequest("set-key", value);

		Assert.assertEquals(value, request.getValue());
	}

	@Test
	public void toStringShouldCreateStringWithReadableInfo() {
		final SetRequest request = new SetRequest("set-key", "set-value");

		Assert.assertEquals("SetRequest: { key: \"set-key\", value: \"set-value\" }", request.toString());
	}
}
