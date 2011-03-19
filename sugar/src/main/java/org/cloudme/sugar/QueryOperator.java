package org.cloudme.sugar;

import com.googlecode.objectify.Query;

public interface QueryOperator {
    <T> Query<T> apply(Query<T> query);
}
