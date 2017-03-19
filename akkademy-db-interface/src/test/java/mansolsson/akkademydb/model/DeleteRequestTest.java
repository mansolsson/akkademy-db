package mansolsson.akkademydb.model;

import org.junit.Assert;
import org.junit.Test;

public class DeleteRequestTest {
	@Test
	public void constructorTakingKeyParamShouldSetKey() {
		final String key = "delete-key";

		final DeleteRequest request = new DeleteRequest(key);

		Assert.assertEquals(key, request.getKey());
	}

	@Test
	public void toStringShouldCreateStringWithReadableInfo() {
		final String key = "delete-key";

		final DeleteRequest request = new DeleteRequest(key);

		Assert.assertEquals("DeleteRequest: { key: \"delete-key\" }", request.toString());
	}
}
