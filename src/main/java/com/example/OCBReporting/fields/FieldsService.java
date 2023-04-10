package com.example.OCBReporting.fields;

import com.example.OCBReporting.fields.dto.FieldRequest;
import com.example.OCBReporting.model.Field;
import com.example.OCBReporting.model.Table;
import com.example.OCBReporting.repository.FieldRepository;
import com.example.OCBReporting.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final FieldRepository fieldRepo;

    public Field create(FieldRequest table) {
        return fieldRepo.save(
                Field.builder()
                        .name(table.getName())
                        .tableId(table.getTable_id())
                        .build()
        );
    }

    public List<Field> byTable(int tableId) {
        return fieldRepo.findByTableId(tableId);
    }
}
