package com.example.queriesmantenimientos.apps;

import com.example.queriesmantenimientos.apps.dto.AppRequest;
import com.example.queriesmantenimientos.model.App;
import com.example.queriesmantenimientos.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppsService {
    private final AppRepository appRepo;

    public App create(AppRequest app) {
        return appRepo.save(App.builder().name(app.getName()).status(App.STATUS_ACTIVE).build());
    }

    public List<App> all() {
        return appRepo.findAll();
    }
}
