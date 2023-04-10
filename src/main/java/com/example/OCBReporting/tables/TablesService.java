package com.example.OCBReporting.tables;

import com.example.OCBReporting.model.Table;
import com.example.OCBReporting.repository.TableRepository;
import com.example.OCBReporting.tables.dto.TableRequest;
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
