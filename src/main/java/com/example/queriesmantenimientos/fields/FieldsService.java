package com.example.queriesmantenimientos.fields;

import com.example.queriesmantenimientos.fields.dto.FieldRequest;
import com.example.queriesmantenimientos.model.Field;
import com.example.queriesmantenimientos.repository.FieldRepository;
import com.example.queriesmantenimientos.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final FieldRepository fieldRepo;
    private final AuditLogService auditLogService;

    public Optional<Field> update(
            long id, FieldRequest field,
            String authorization
    ) {
        Optional<Field> fieldRes = fieldRepo.findById(
                id
        ).map(x -> {
            if (field.getName() != null) x.setName(field.getName());
            if (field.getType() != null) x.setType(field.getType());
            return fieldRepo.save(x);
        });
        auditLogService.audit("update field",fieldRes,authorization);
        return fieldRes;
    }

    public Field create(FieldRequest field,String authorization) {
        Field newfield = fieldRepo.save(
                Field.builder()
                        .name(field.getName())
                        .type(field.getType())
                        .tableId(field.getTable_id())
                        .build()
        );
        auditLogService.audit("create field",newfield,authorization);
        return newfield;
    }

    public List<Field> byTable(int tableId) {
        return fieldRepo.findByTableId(tableId);
    }
}
