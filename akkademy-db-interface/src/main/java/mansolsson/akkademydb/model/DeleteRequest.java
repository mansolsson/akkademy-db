package mansolsson.akkademydb.model;

import java.io.Serializable;

public class DeleteRequest implements Serializable {
	private static final long serialVersionUID = -6216191644708031445L;

	private final String key;

	public DeleteRequest(final String key) {
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
