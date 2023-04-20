package com.example.queriesmantenimientos.utils;

import com.example.queriesmantenimientos.model.Table;


public class TableUtils {
    public static void isActionAllowed(Table table, int actionId) throws Exception {
        if (table.getActions().stream().noneMatch(a -> a.getId() == actionId))
            throw new Exception("Action not allowed");
    }
}
