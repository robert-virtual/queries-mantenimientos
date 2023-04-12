package com.example.queriesmantenimientos.tables;

import com.example.queriesmantenimientos.model.Table;
import com.example.queriesmantenimientos.repository.TableRepository;
import com.example.queriesmantenimientos.tables.dto.TableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TablesService {
    private final TableRepository tableRepo;

    public Table create(TableRequest table) {
        return tableRepo.save(
                Table.builder()
                        .name(table.getName())
                        .appId(table.getApp_id())
                        .build()
        );
    }

    public List<Table> byApp(int appId) {
        return tableRepo.findByAppId(appId);
    }
}
