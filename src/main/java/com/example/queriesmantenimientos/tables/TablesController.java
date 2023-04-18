package com.example.queriesmantenimientos.tables;

import com.example.queriesmantenimientos.dto.BasicResponse;
import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.tables.dto.TableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tables")
@RequiredArgsConstructor
public class TablesController {
    private final TablesService tablesService;

    @GetMapping("{app_id}")
    public ResponseEntity<BasicResponse<List<Table>>> byApp(
            @PathVariable int app_id
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<List<Table>>builder()
                        .data(tablesService.byApp(app_id))
                        .build()
        );
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse<Table>> create(
            @RequestBody TableRequest table
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Table>builder()
                        .data(tablesService.create(table))
                        .build()
        );
    }
}