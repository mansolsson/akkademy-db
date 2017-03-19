package mansolsson.akkademydb.model;

import java.io.Serializable;

public class GetRequest implements Serializable {
	private static final long serialVersionUID = -8437469914194503082L;

	private final String key;

	public GetRequest(final String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": { key: \"" + this.key + "\" }";
	}
}
