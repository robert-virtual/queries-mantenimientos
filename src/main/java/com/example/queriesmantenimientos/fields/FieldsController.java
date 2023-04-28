package com.example.queriesmantenimientos.fields;

import com.example.queriesmantenimientos.dto.BasicResponse;
import com.example.queriesmantenimientos.fields.dto.FieldRequest;
import com.example.queriesmantenimientos.model.Field;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fields")
@RequiredArgsConstructor
public class FieldsController {
    private final FieldsService fieldsService;

    @GetMapping("{table_id}")
    public ResponseEntity<BasicResponse<List<Field>>> byApp(
            @PathVariable int table_id
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<List<Field>>builder()
                        .data(fieldsService.byTable(table_id))
                        .build()
        );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<BasicResponse<Optional<Field>>> update(
            @RequestBody FieldRequest field,
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Optional<Field>>builder()
                        .data(fieldsService.update(id, field,authorization))
                        .build()
        );
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse<Field>> create(
            @RequestBody FieldRequest field,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Field>builder()
                        .data(fieldsService.create(field,authorization))
                        .build()
        );
    }
}
