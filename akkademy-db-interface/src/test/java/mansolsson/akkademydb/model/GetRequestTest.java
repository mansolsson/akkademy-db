package mansolsson.akkademydb.model;

import org.junit.Assert;
import org.junit.Test;

public class GetRequestTest {
	@Test
	public void constructorTakingKeyParamShouldSetKey() {
		final String key = "get-key";

		final GetRequest request = new GetRequest(key);

		Assert.assertEquals(key, request.getKey());
	}

	@Test
	public void toStringShouldCreateStringWithReadableInfo() {
		final String key = "get-key";

		final GetRequest request = new GetRequest(key);

		Assert.assertEquals("GetRequest: { key: \"get-key\" }", request.toString());
	}
}
