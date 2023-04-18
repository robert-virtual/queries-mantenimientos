package com.example.queriesmantenimientos.utils;

import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;

import java.util.Objects;
import java.util.Optional;

public class QueryUtils {
    public static Query isQueryAuthorized(Optional<Query> query) throws Exception {
        if (query.isEmpty()) throw new Exception("Invalid Query");
        if (Objects.equals(query.get().getStatus(), QueryStatus.STATUS_AUTHORIZED.toString()))
            throw new Exception("Invalid Query");
        return query.get();
    }
    public static void tableExists(QueryRequest query) throws Exception {
        if (
                (query.getAction_id() == Query.ACTION_UPDATE || query.getAction_id() == Query.ACTION_DELETE)
                        && query.getWhere().isEmpty()
        ) {
            throw new Exception("You have to provide a where condition to be updated or deleted");
        }
    }
    public static void hasWhere(QueryRequest query) throws Exception {
        if (
                (query.getAction_id() == Query.ACTION_UPDATE || query.getAction_id() == Query.ACTION_DELETE)
                        && query.getWhere().isEmpty()
        ) {
            throw new Exception("You have to provide a where condition to be updated or deleted");
        }
    }
}
