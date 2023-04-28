package com.example.queriesmantenimientos.queries;

import com.example.queriesmantenimientos.AuditLogService;
import com.example.queriesmantenimientos.config.JwtService;
import com.example.queriesmantenimientos.model.*;
import com.example.queriesmantenimientos.dto.QueryWithParameters;
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

import javax.transaction.Transactional;
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
    private final AuditLogService auditLogService;

    @Transactional
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
            Query savedQuery = queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(query.getAction_id()).build())
                            .parameters(objectMapper.writeValueAsString(
                                    query.getParameters() == null
                                            ? new HashMap<>()
                                            : query.getParameters()
                            ))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere() == null ? "{}" : query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
            auditLogService.audit("create query", savedQuery, user);
            return savedQuery;
        } catch (Exception e) {
            log.error(e.getMessage());
            auditLogService.audit("create query failed", e.getMessage(), user);
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


    @Transactional
    public Query authorizeImproved(String authorization, long query_id) throws Exception {
        // check user has role to authorize queries
        User user = jwtService.getUser(authorization);
        UserUtils.checkUserRole(user, RoleValues.QUERY_AUTHORIZER);
        // verify that the query status is not authorized
        Query query = queryRepo.findById(query_id).orElseThrow(() -> new Exception("Invalid query"));
        QueryUtils.isQueryAuthorized(query);
        // create the query string
        Map<String, String> queryTemplates = new HashMap<>();
        queryTemplates.put("insert", "insert into %s(%s) values(%s)"); // table, fields, placeholders
        queryTemplates.put("update", "update %s set %s where %s"); // table, field=placeholders, where-conditions
        queryTemplates.put("delete", "delete from %s where %s"); // table, where-conditions
        String queryStructure = queryTemplates.get(query.getAction().getName());
        log.info(queryStructure);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> queryParameters = objectMapper.readValue(query.getParameters(), new TypeReference<>() {
        });
        List<String> fieldList = new ArrayList<>(queryParameters.keySet());
        String joinedFields = String.join(",", fieldList);
        List<String> fieldPlaceholders = fieldList.stream().map((field) -> "?").collect(Collectors.toList()); // List.Of("?","?",...)
        Map<String, List<Object>> parameters = new HashMap<>();
        List<Object> values = new ArrayList<>(queryParameters.values());
        parameters.put(
                "insert",
                List.of(
                        query.getTable().getName(),
                        joinedFields,
                        String.join(",", fieldPlaceholders)
                )
        ); // table, fields, placeholders
        if (query.getAction().getId() == Query.ACTION_INSERT) {
            String queryCompleted = String.format(queryStructure, parameters.get(query.getAction().getName()).toArray());
            log.info(queryCompleted);
            log.info(objectMapper.writeValueAsString(values));
            // send query string to the service that will execute it
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                    QueryWithParameters.builder()
                            .query(queryCompleted)
                            .values(values.toArray())
                            .build(),
                    headers
            );
            App app = query.getTable().getApp();
            String queryResponse = restTemplate.postForObject(
                    app.getExecuteQueryEndpoint(),
                    request,
                    String.class
            );
            // update query
            query.setStatus(QueryStatus.AUTHORIZED.toString());
            query.setAuthorizedAt(LocalDateTime.now());
            query.setResponse(queryResponse);
            query.setAuthorizedBy(user);
            return queryRepo.save(query);

        }
        List<String> fieldPlaceholderList = fieldList.stream().map(field -> String.format("%s=?", field)).collect(Collectors.toList());// field1=?,field2=?

        // when query acton is insert queryWhere is empty
        Map<String, Object> queryWhere = objectMapper.readValue(query.getWhereCondition(), new TypeReference<>() {
        }); // {} // {id:3} // {and:[{email:"..."},{lastname:"..."}]} // {or:[{email:"..."},{lastname:"..."}]}
        String operatorOrKey = queryWhere.keySet().toArray()[0].toString();
        List<Map<String, Object>> whereParameters = queryWhere.get(operatorOrKey).getClass() == ArrayList.class
                ? (List<Map<String, Object>>) queryWhere.get(operatorOrKey)
                : new ArrayList<>();
        List<String> whereParametersPlaceholders = whereParameters.stream().map(
                map -> String.format("%s=?", map.keySet().toArray()[0].toString())
        ).collect(Collectors.toList());
        boolean hasOperator = List.of("and", "or").contains(operatorOrKey);
        String whereConditions = hasOperator
                ? String.join(String.format(" %s ", operatorOrKey), whereParametersPlaceholders)
                : String.format("%s=?", operatorOrKey); // key1 = ? // key1 = ? or key2 = ? or ... // key1 = ? and key2 = ? and ...
        values.addAll(hasOperator ? whereParameters.stream().map(
                map -> map.get(map.keySet().toArray()[0].toString())
        ).collect(Collectors.toList()) : List.of(queryWhere.get(operatorOrKey)));
        parameters.put(
                "update",
                List.of(
                        query.getTable().getName(),
                        String.join(",", fieldPlaceholderList),
                        whereConditions
                )
        ); // table, field=placeholders, where-conditions
        parameters.put(
                "delete",
                List.of(
                        query.getTable().getName(),
                        whereConditions
                )
        ); // table, where-conditions
        String queryCompleted = String.format(queryStructure, parameters.get(query.getAction().getName()).toArray());
        log.info(queryCompleted);
        log.info(objectMapper.writeValueAsString(values));
        // send query string to the service that will execute it
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<QueryWithParameters> request = new HttpEntity<>(
                QueryWithParameters.builder()
                        .query(queryCompleted)
                        .values(values.toArray())
                        .build(),
                headers
        );
        App app = query.getTable().getApp();
        String queryResponse = restTemplate.postForObject(
                app.getExecuteQueryEndpoint(),
                request,
                String.class
        );
        // update query
        query.setStatus(QueryStatus.AUTHORIZED.toString());
        query.setAuthorizedAt(LocalDateTime.now());
        query.setResponse(queryResponse);
        query.setAuthorizedBy(user);
        queryRepo.save(query);
        auditLogService.audit("authorize query", Query.builder().id(query_id).build(), user);
        return query;
    }

}
