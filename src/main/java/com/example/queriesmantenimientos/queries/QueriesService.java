package com.example.queriesmantenimientos.queries;

import com.example.queriesmantenimientos.model.Action;
import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.model.User;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;
import com.example.queriesmantenimientos.repository.QueryRepository;
import com.example.queriesmantenimientos.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueriesService {
    private final QueryRepository queryRepo;
    private final UserRepository userRepo;

    public Query create(QueryRequest query) throws Exception {
        if (
                (query.getAction_id() == Query.ACTION_UPDATE || query.getAction_id() == Query.ACTION_DELETE)
                        && query.getParameters().get("id") == null
        ) {
            throw new Exception("You must provide a id to be updated or deleted");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findOneByEmail(userEmail).orElseThrow();
        try {
            return queryRepo.save(
                    Query.builder()
                            .table(Table.builder().id(query.getTable_id()).build())
                            .action(Action.builder().id(query.getAction_id()).build())
                            .parameters(objectMapper.writeValueAsString(query.getParameters()))
                            .status(Query.STATUS_REQUESTED)
                            .requestedBy(user)
                            .requestedAt(LocalDateTime.now())
                            .build()
            );
        } catch (JsonProcessingException e) {
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
