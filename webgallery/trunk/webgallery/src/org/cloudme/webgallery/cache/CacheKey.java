// created 30.12.2009 10:28:46 by Moritz Petersen
package org.cloudme.webgallery.cache;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CacheKey implements Serializable {
	private static final long serialVersionUID = 8269975340644775472L;
	private final Serializable[] parameters;

	public CacheKey(Serializable... parameters) {
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] == null) {
				throw new IllegalArgumentException("Parameters must not be null: " + Arrays.asList(parameters));
			}
		}
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		CacheKey key = (CacheKey) obj;
		return new EqualsBuilder().append(parameters, key.parameters).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(parameters).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(parameters).toString();
	}
}