package com.example.queriesmantenimientos.utils;

import com.example.queriesmantenimientos.model.Query;

import java.util.Objects;
import java.util.Optional;

public class QueryUtils {
    public static Query validateQuery(Optional<Query> query) throws Exception {
        if (query.isEmpty()) throw new Exception("Invalid Query");
        if (Objects.equals(query.get().getStatus(), QueryStatus.STATUS_AUTHORIZED.toString()))
            throw new Exception("Invalid Query");
        return query.get();
    }
}
