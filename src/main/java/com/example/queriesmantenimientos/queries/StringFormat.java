package com.example.queriesmantenimientos.queries;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class StringFormat {
    public static void main(String[] args) {
        String res = String.format("%s %s %s", List.of("val1", "val2", "val3").toArray());
        System.out.println(res);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = objectMapper.readValue("{\"and\":[{\"name\":\"robert\"},{\"lastname\":\"castillo\"}]}", new TypeReference<>() {
            });
            String operatorOrKey = jsonMap.keySet().toArray()[0].toString();
            System.out.println(jsonMap.get(operatorOrKey).getClass());
            List<Map<String, Object>> list = (List<Map<String, Object>>) jsonMap.get(operatorOrKey);
            System.out.println(list.getClass() == ArrayList.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}