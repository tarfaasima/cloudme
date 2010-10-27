package org.cloudme.gaestripes;

import com.googlecode.objectify.Query;

public interface QueryOperator {
    <T> Query<T> apply(Query<T> query);
}
