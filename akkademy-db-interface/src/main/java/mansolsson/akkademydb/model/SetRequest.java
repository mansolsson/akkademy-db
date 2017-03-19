package mansolsson.akkademydb.model;

import java.io.Serializable;

public class SetRequest implements Serializable {
	private static final long serialVersionUID = 8185106363079850720L;

	private final String key;
	private final Object value;

	public SetRequest(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": { key: \"" + this.key + "\", value: \"" + this.value + "\" }";
	}
}
