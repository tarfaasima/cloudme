package org.cloudme.gaestripes;

import com.googlecode.objectify.Query;

public interface QueryOperator {
    <T> Query<T> appendToQuery(Query<T> query);
}
