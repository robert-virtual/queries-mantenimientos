package com.example.OCBReporting.fields;

import com.example.OCBReporting.fields.dto.FieldRequest;
import com.example.OCBReporting.model.Field;
import com.example.OCBReporting.model.Table;
import com.example.OCBReporting.repository.FieldRepository;
import com.example.OCBReporting.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final FieldRepository fieldRepo;

    public Optional<Field> update(long id, FieldRequest field) {
        return fieldRepo.findById(
                id
        ).map(x -> {
            if (field.getName() != null) x.setName(field.getName());
            if (field.getType() != null) x.setType(field.getType());
            return fieldRepo.save(x);
        });

    }

    public Field create(FieldRequest field) {
        return fieldRepo.save(
                Field.builder()
                        .name(field.getName())
                        .type(field.getType())
                        .tableId(field.getTable_id())
                        .build()
        );
    }

    public List<Field> byTable(int tableId) {
        return fieldRepo.findByTableId(tableId);
    }
}
