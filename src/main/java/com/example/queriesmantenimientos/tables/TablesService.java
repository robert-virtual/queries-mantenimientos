package com.example.queriesmantenimientos.tables;

import com.example.queriesmantenimientos.AuditLogService;
import com.example.queriesmantenimientos.config.JwtService;
import com.example.queriesmantenimientos.dto.BasicResponse;
import com.example.queriesmantenimientos.model.*;
import com.example.queriesmantenimientos.repository.ActionRepository;
import com.example.queriesmantenimientos.repository.TableRepository;
import com.example.queriesmantenimientos.tables.dto.TableAndActions;
import com.example.queriesmantenimientos.tables.dto.TableRequest;
import com.example.queriesmantenimientos.utils.RoleValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TablesService {
    private final TableRepository tableRepo;
    private final ActionRepository actionRepo;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;

    public BasicResponse<Table> create(String authorization, TableRequest table) {
        User user;
        try {
            user = jwtService.getUser(authorization);
        } catch (Exception e) {
            return BasicResponse.<Table>builder().error("Unauthorized").build();
        }
        boolean hasPermission = user.getRoles().stream().map(Role::getName)
                .filter(Objects::nonNull)
                .anyMatch(Set.of(
                        RoleValues.QUERY_CREATOR.toString(), RoleValues.QUERY_AUTHORIZER.toString()
                )::contains);
        if (!hasPermission)
            return BasicResponse.<Table>builder().error("User does not have permission to create tables").build();
        List<Action> actions = actionRepo.findByIdIn(table.getActions());
        if (actions.size() != table.getActions().size())
            return BasicResponse.<Table>builder().error("Invalid action").build();
        auditLogService.audit("create table", table, user);
        return BasicResponse.<Table>builder().data(tableRepo.save(
                Table.builder()
                        .name(table.getName())
                        .app(App.builder().id(table.getApp_id()).build())
                        .actions(
                                table.getActions().stream()
                                        .map(a -> Action.builder().id(a).build()).collect(Collectors.toList())
                        )
                        .build()
        )).build();
    }

    public List<Table> byApp(int appId) {
        return tableRepo.findByAppId(appId);
    }

    public Table addActions(TableAndActions tableAndActions) {
        Table table = tableRepo.findById(tableAndActions.getTable_id()).orElseThrow();
        Set<Integer> actions = table.getActions().stream().map(Action::getId).collect(Collectors.toSet());
        actions.addAll(tableAndActions.getActions());
        table.setActions(actions.stream().map(a -> Action.builder().id(a).build()).collect(Collectors.toList()));
        tableRepo.save(table);
        return table;
    }
}
