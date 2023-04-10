package com.example.OCBReporting.fields;

import com.example.OCBReporting.dto.BasicResponse;
import com.example.OCBReporting.fields.dto.FieldRequest;
import com.example.OCBReporting.model.Field;
import com.example.OCBReporting.model.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("create")
    public ResponseEntity<BasicResponse<Field>> create(
            @RequestBody FieldRequest table
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<Field>builder()
                        .data(fieldsService.create(table))
                        .build()
        );
    }
}
