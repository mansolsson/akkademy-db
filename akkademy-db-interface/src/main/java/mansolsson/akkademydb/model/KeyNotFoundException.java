package mansolsson.akkademydb.model;

import java.io.Serializable;

public class KeyNotFoundException extends Exception implements Serializable {
	private static final long serialVersionUID = -7054352773349232285L;

	private final String key;

	public KeyNotFoundException(final String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
