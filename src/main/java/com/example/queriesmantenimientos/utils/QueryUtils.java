package com.example.queriesmantenimientos.utils;

import com.example.queriesmantenimientos.model.Action;
import com.example.queriesmantenimientos.model.Field;
import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryUtils {
    public static void isQueryAuthorized(Query query) throws Exception {
        if (Objects.equals(query.getStatus(), QueryStatus.AUTHORIZED.toString()))
            throw new Exception("Invalid Query");
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
                        && (query.getWhere() == null || query.getWhere().isEmpty())
        ) {
            throw new Exception("You have to provide a where condition to be updated or deleted");
        }
    }

    public static void validateFields(QueryRequest query, Table table) throws Exception {
        Set<String> keys = query.getParameters().keySet();
        for (String key : keys) {
            if (!table.getFields().stream().map(Field::getName).collect(Collectors.toList()).contains(key)) {
                throw new Exception("invalid field");
            }

        }
    }

    public static void validateAction(List<Action> actions, int actionId) throws Exception {
        if (!Set.of(actions.stream().map(Action::getId)).contains(actionId)) {
            throw new Exception("Invalid action");
        }
    }
}
