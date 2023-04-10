package com.example.OCBReporting.queries;

import com.example.OCBReporting.dto.BasicResponse;
import com.example.OCBReporting.model.Query;
import com.example.OCBReporting.queries.dto.QueryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries")
@RequiredArgsConstructor
public class QueriesController {
    private final QueriesService queriesService;

    @GetMapping("/all")
    public ResponseEntity<BasicResponse<List<Query>>> createQuery(
            @RequestParam(name = "status") String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                BasicResponse
                        .<List<Query>>builder()
                        .data(status != null ? queriesService.byStatus(status, page, size) : queriesService.paged(page, size))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<BasicResponse<Query>> createQuery(
            @RequestBody QueryRequest query
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Query>builder()
                        .data(queriesService.create(query))
                        .build()
        );
    }
}
