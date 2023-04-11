package com.example.OCBReporting.fields;

import com.example.OCBReporting.dto.BasicResponse;
import com.example.OCBReporting.fields.dto.FieldRequest;
import com.example.OCBReporting.model.Field;
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
            @PathVariable long id
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Optional<Field>>builder()
                        .data(fieldsService.update(id, field))
                        .build()
        );
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse<Field>> create(
            @RequestBody FieldRequest field
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Field>builder()
                        .data(fieldsService.create(field))
                        .build()
        );
    }
}
