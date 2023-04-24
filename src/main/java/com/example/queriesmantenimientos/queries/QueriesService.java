package com.example.queriesmantenimientos.queries;

import com.example.queriesmantenimientos.config.JwtService;
import com.example.queriesmantenimientos.model.App;
import com.example.queriesmantenimientos.dto.QueryWithParameters;
import com.example.queriesmantenimientos.model.Action;
import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.model.User;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;
import com.example.queriesmantenimientos.repository.QueryRepository;
import com.example.queriesmantenimientos.repository.TableRepository;
import com.example.queriesmantenimientos.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueriesService {
    private final QueryRepository queryRepo;
    private final JwtService jwtService;
    private final TableRepository tableRepo;

    public Query create(QueryRequest query, String authorization) throws Exception {
        QueryUtils.hasWhere(query);
        Table table = tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        if (query.getAction_id() == Query.ACTION_UPDATE || query.getAction_id() == Query.ACTION_INSERT) {
            QueryUtils.validateFields(query, table);
        }
        TableUtils.isActionAllowed(table, query.getAction_id());
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        UserUtils.hasRolePermission(user, Set.of(RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()));
        hasTablePermissions(query, user);
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(query.getAction_id()).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Query createInsert(QueryRequest query, String authorization) throws Exception {
        Table table = tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        QueryUtils.validateFields(query, table);
        TableUtils.isActionAllowed(table, Query.ACTION_INSERT);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        UserUtils.hasRolePermission(user, Set.of(RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()));
        hasTablePermissions(query, user);
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(Query.ACTION_INSERT).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(null)
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Query createUpdateWhere(QueryRequest query, String authorization) throws Exception {
        QueryUtils.hasWhere(query);
        Table table = tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        QueryUtils.validateFields(query, table);
        TableUtils.isActionAllowed(table, Query.ACTION_UPDATE);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        UserUtils.hasRolePermission(user, Set.of(RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()));
        hasTablePermissions(query, user);
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(Query.ACTION_UPDATE).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Query createUpdate(QueryRequest query, String authorization) throws Exception {
        QueryUtils.hasWhere(query);
        Table table = tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        QueryUtils.validateFields(query, table);
        TableUtils.isActionAllowed(table, Query.ACTION_UPDATE);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        UserUtils.hasRolePermission(user, Set.of(RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()));
        hasTablePermissions(query, user);
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(Query.ACTION_UPDATE).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Query createDelete(QueryRequest query, String authorization) throws Exception {
        QueryUtils.hasWhere(query);
        Table table = tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        TableUtils.isActionAllowed(table, Query.ACTION_DELETE);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        UserUtils.hasRolePermission(user, Set.of(RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()));
        hasTablePermissions(query, user);
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(Query.ACTION_DELETE).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void hasTablePermissions(QueryRequest query, User user) throws Exception {
        boolean hasTablePermission = user.getApps().stream()
                .flatMap(a -> tableRepo.findByAppId(a.getId()).stream())
                .anyMatch(t -> t.getId() == query.getTable_id());
        if (!hasTablePermission) {
            throw new Exception("You do not have enough permissions");
        }
    }


    public List<Query> byStatus(
            String status,
            int page, int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return queryRepo.findAllByStatusOrderByRequestedAt(status, pageable);
    }

    public List<Query> paged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return queryRepo.findAll(pageable).getContent();
    }

    public Optional<Query> byId(long id) {
        return queryRepo.findById(id);
    }

    public Query authorize(String authorization, long query_id) throws Exception {
        Optional<Query> optionalQuery = queryRepo.findById(query_id);
        Query query = QueryUtils.isQueryAuthorized(optionalQuery);
        User user = jwtService.getUser(authorization);
        UserUtils.checkUserRole(user, RoleValues.QUERY_AUTHORIZER);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parameters = objectMapper.readValue(query.getParameters(), new TypeReference<>() {
        });
        Map<String, Object> where = objectMapper.readValue(query.getWhereCondition(), new TypeReference<>() {
        });
        Object whereKey = where != null ? where.keySet().toArray()[0] : null;
        Object whereKeyValue = where != null ? where.get(whereKey == null ? "id" : whereKey) : null;
        Object id = whereKeyValue == null ? 0 : whereKeyValue;
        String fieldsString = String.join(",", parameters.keySet());//field1,field2
        List<String> fields = parameters.keySet().stream().map(k -> String.format("%s=?", k)).collect(Collectors.toList());//field1=?,field2=?
        if (whereKey != null) {
            parameters.put(whereKey.toString(), id);
        }
        String valuesPlaceholders = String.join(",", Collections.nCopies(parameters.size(), "?")); // ?,?
        String queryResponse = "";
        String queryString = "";
        App app = query.getTable().getApp();
        if (app == null) throw new Exception("Invalid query");
        switch (query.getAction().getId()) {
            case Query.ACTION_INSERT:
                queryString = String.format(
                        "insert into %s(%s) values(%s)",
                        query.getTable().getName(),
                        fieldsString,
                        valuesPlaceholders
                );

                break;
            case Query.ACTION_UPDATE:
                queryString = String.format(
                        "update %s set %s where %s = ?",
                        query.getTable().getName(),
                        String.join(",", fields),
                        whereKey
                );
                break;
            case Query.ACTION_DELETE:
                queryString = String.format(
                        "delete from %s where %s = ?",
                        query.getTable().getName(),
                        whereKey
                );
                break;
        }
        log.info(queryString);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                QueryWithParameters.builder()
                        .query(queryString)
                        .values(parameters.values().toArray())
                        .build(),
                headers
        );
        queryResponse = restTemplate.postForObject(
                app.getExecuteQueryEndpoint(),
                request,
                String.class
        );
        query.setStatus(QueryStatus.AUTHORIZED.toString());
        query.setAuthorizedAt(LocalDateTime.now());
        query.setResponse(queryResponse);
        query.setAuthorizedBy(user);
        return queryRepo.save(query);
    }

    public Query authorizeInsert(String authorization, long query_id) throws Exception {
        Optional<Query> optionalQuery = queryRepo.findById(query_id);
        Query query = QueryUtils.isQueryAuthorized(optionalQuery);
        if (query.getAction().getId() != Query.ACTION_DELETE) throw new Exception("bad request this endpoint is intended for insert queries");
        User user = jwtService.getUser(authorization);
        UserUtils.checkUserRole(user, RoleValues.QUERY_AUTHORIZER);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parameters = objectMapper.readValue(query.getParameters(), new TypeReference<>() {
        });
        String fieldsString = String.join(",", parameters.keySet());//field1,field2
        String valuesPlaceholders = String.join(",", Collections.nCopies(parameters.size(), "?")); // ?,?
        String queryResponse = "";
        String queryString = "";
        App app = query.getTable().getApp();
        if (app == null) throw new Exception("Invalid query");
        queryString = String.format(
                "insert into %s(%s) values(%s)",
                query.getTable().getName(),
                fieldsString,
                valuesPlaceholders
        );
        log.info(queryString);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                QueryWithParameters.builder()
                        .query(queryString)
                        .values(parameters.values().toArray())
                        .build(),
                headers
        );
        queryResponse = restTemplate.postForObject(
                app.getExecuteQueryEndpoint(),
                request,
                String.class
        );
        query.setStatus(QueryStatus.AUTHORIZED.toString());
        query.setAuthorizedAt(LocalDateTime.now());
        query.setResponse(queryResponse);
        query.setAuthorizedBy(user);
        return queryRepo.save(query);
    }

    public Query authorizeDelete(String authorization, long query_id) throws Exception {
        Optional<Query> optionalQuery = queryRepo.findById(query_id);
        Query query = QueryUtils.isQueryAuthorized(optionalQuery);
        if (query.getAction().getId() != Query.ACTION_DELETE) throw new Exception("bad request this endpoint is intended for delete queries");
        User user = jwtService.getUser(authorization);
        UserUtils.checkUserRole(user, RoleValues.QUERY_AUTHORIZER);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parameters = objectMapper.readValue(query.getParameters(), new TypeReference<>() {
        });
        List<Object> parametersValues = new ArrayList<>();
        Map<String, Object> where = objectMapper.readValue(query.getWhereCondition(), new TypeReference<>() {
        });
        Object firstWhereKey = where != null ? where.keySet().toArray()[0] : null;

        List<String> filterFields = new ArrayList<>();//field1=?,field2=?

        Object whereKeyValue = where != null ? where.get(firstWhereKey == null ? "id" : firstWhereKey) : null;
        Object id = whereKeyValue == null ? 0 : whereKeyValue;
        if (firstWhereKey == "or" || firstWhereKey == "and") {
            // key1 = value1 && key2 == value2
            // key1 = value1 || key2 == value2
            // where key1 = ? || key2 == ?

            // where = {or:[{name:"name1"},{name:"name2"}]}
            List<Map<String, Object>> whereKeys = (List<Map<String, Object>>) where.get(firstWhereKey);
            filterFields = whereKeys.stream().map(
                    k -> String.format("%s=?", k.keySet().toArray()[0])
            ).collect(Collectors.toList());//field1=?,field2=?
            whereKeys.forEach(k -> {
                parametersValues.add(k.get(k.keySet().toArray()[0].toString()));
            });

        } else {
            // where = {id:1}
            filterFields.add(String.format("%s=?", firstWhereKey));
//            parameters.put(firstWhereKey.toString(), id);
            parametersValues.add(id);
        }
        String queryResponse = "";
        String queryString = "";
        App app = query.getTable().getApp();
        if (app == null) throw new Exception("Invalid query");
        queryString = String.format(
                "delete from %s where %s",
                query.getTable().getName(),
                String.join(String.format(" %s ", firstWhereKey), filterFields)
        );
        log.info(queryString);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                QueryWithParameters.builder()
                        .query(queryString)
                        .values(parametersValues.toArray())
                        .build(),
                headers
        );
        queryResponse = restTemplate.postForObject(
                app.getExecuteQueryEndpoint(),
                request,
                String.class
        );
        query.setStatus(QueryStatus.AUTHORIZED.toString());
        query.setAuthorizedAt(LocalDateTime.now());
        query.setResponse(queryResponse);
        query.setAuthorizedBy(user);
        return queryRepo.save(query);
    }

    public Query authorizeUpdate(String authorization, long query_id) throws Exception {
        Optional<Query> optionalQuery = queryRepo.findById(query_id);
        Query query = QueryUtils.isQueryAuthorized(optionalQuery);
        if (query.getAction().getId() != Query.ACTION_UPDATE) throw new Exception("bad request this endpoint is intended for update queries");
        User user = jwtService.getUser(authorization);
        UserUtils.checkUserRole(user, RoleValues.QUERY_AUTHORIZER);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parameters = objectMapper.readValue(query.getParameters(), new TypeReference<>() {
        });
        List<Object> parametersValues = new ArrayList<>(Arrays.asList(parameters.values().toArray()));
        Map<String, Object> where = objectMapper.readValue(query.getWhereCondition(), new TypeReference<>() {
        });
        Object firstWhereKey = where != null ? where.keySet().toArray()[0] : null;
        List<String> filterFields = new ArrayList<>();//field1=?,field2=?

        Object whereKeyValue = where != null ? where.get(firstWhereKey == null ? "id" : firstWhereKey) : null;
        Object id = whereKeyValue == null ? 0 : whereKeyValue;
        if (firstWhereKey == "or" || firstWhereKey == "and") {
            // key1 = value1 && key2 == value2
            // key1 = value1 || key2 == value2
            // where key1 = ? || key2 == ?

            // where = {or:[{name:"name1"},{name:"name2"}]}
            List<Map<String, Object>> whereKeys = (List<Map<String, Object>>) where.get(firstWhereKey);
            filterFields = whereKeys.stream().map(
                    k -> String.format("%s=?", k.keySet().toArray()[0])
            ).collect(Collectors.toList());//field1=?,field2=?
            whereKeys.forEach(k -> {
                parametersValues.add(k.get(k.keySet().toArray()[0].toString()));
            });

        } else {
            // where = {id:1}
            filterFields.add(String.format("%s=?", firstWhereKey));
//            parameters.put(firstWhereKey.toString(), id);
            parametersValues.add(id);
        }
        List<String> fields = parameters.keySet().stream().map(
                k -> String.format("%s=?", k)
        ).collect(Collectors.toList());//field1=?,field2=?
        String queryResponse = "";
        String queryString = "";
        App app = query.getTable().getApp();
        if (app == null) throw new Exception("Invalid query");
        queryString = String.format(
                "update %s set %s where %s", //"update %s set %s where %s = ?"
                query.getTable().getName(),
                String.join(",", fields),
                String.join(String.format(" %s ", firstWhereKey), filterFields)
        );
        log.info(queryString);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                QueryWithParameters.builder()
                        .query(queryString)
                        .values(parametersValues.toArray())
                        .build(),
                headers
        );
        queryResponse = restTemplate.postForObject(
                app.getExecuteQueryEndpoint(),
                request,
                String.class
        );
        query.setStatus(QueryStatus.AUTHORIZED.toString());
        query.setAuthorizedAt(LocalDateTime.now());
        query.setResponse(queryResponse);
        query.setAuthorizedBy(user);
        return queryRepo.save(query);
    }

}
