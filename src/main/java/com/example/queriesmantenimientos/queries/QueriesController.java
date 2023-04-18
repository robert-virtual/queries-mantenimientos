package com.example.queriesmantenimientos.queries;

import com.example.queriesmantenimientos.dto.BasicResponse;
import com.example.queriesmantenimientos.model.Query;
import com.example.queriesmantenimientos.queries.dto.QueryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/queries")
@RequiredArgsConstructor
public class QueriesController {
    private final QueriesService queriesService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Query>> getById(@PathVariable long id) {
        return ResponseEntity.ok(queriesService.byId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<BasicResponse<List<Query>>> all(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                BasicResponse
                        .<List<Query>>builder()
                        .data(
                                status != null
                                        ? queriesService.byStatus(status, page, size)
                                        : queriesService.paged(page, size)
                        )
                        .data_count(size)
                        .page(page)
                        .data_type("Query[]")
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<BasicResponse<Query>> createQuery(
            @RequestHeader("Authorization") String authorization,
            @RequestBody QueryRequest query
    ) {
        try {
            return ResponseEntity.ok(
                    BasicResponse
                            .<Query>builder()
                            .data(queriesService.create(query, authorization))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    BasicResponse
                            .<Query>builder()
                            .error(e.getMessage())
                            .build()
            );
        }
    }
}
