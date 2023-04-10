package com.example.OCBReporting.queries;

import com.example.OCBReporting.model.Action;
import com.example.OCBReporting.model.Query;
import com.example.OCBReporting.model.Table;
import com.example.OCBReporting.model.User;
import com.example.OCBReporting.queries.dto.QueryRequest;
import com.example.OCBReporting.repository.QueryRepository;
import com.example.OCBReporting.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueriesService {
    private final QueryRepository queryRepo;
    private final UserRepository userRepo;

    public Query create(QueryRequest query) {
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
            throw new RuntimeException(e);
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
