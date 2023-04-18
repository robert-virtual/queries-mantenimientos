package com.example.queriesmantenimientos.queries;

import com.example.queriesmantenimientos.config.JwtService;
import com.example.queriesmantenimientos.model.Action;
import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.dto.User;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;
import com.example.queriesmantenimientos.repository.QueryRepository;
import com.example.queriesmantenimientos.repository.TableRepository;
import com.example.queriesmantenimientos.utils.QueryUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueriesService {
    private final QueryRepository queryRepo;
    private final JwtService jwtService;
    private final TableRepository tableRepo;

    public Query create(QueryRequest query, String authorization) throws Exception {
        QueryUtils.hasWhere(query);
        tableRepo.findById(query.getTable_id()).orElseThrow(() -> new Exception("Invalid query"));
        ObjectMapper objectMapper = new ObjectMapper();
        User user = jwtService.getUser(authorization);
        System.out.printf("user found: %d", user.getId());
        System.out.println(user);
        boolean hasPermission = user.getApps().stream()
                .flatMap(a -> a.getTables().stream())
                .anyMatch(t -> t.getId() == query.getTable_id());
        if (!hasPermission) {
            throw new Exception("You do not have permission to create queries for this table");
        }


        try {
            System.out.println("query saved");
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(query.getAction_id()).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .whereCondition(objectMapper.writeValueAsString(query.getWhere()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user.getId())
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            System.out.printf("query not saved: %s", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
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
}
