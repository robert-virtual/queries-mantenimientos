package com.example.OCBReporting.apps;

import com.example.OCBReporting.apps.dto.AppRequest;
import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Table;
import com.example.OCBReporting.repository.AppRepository;
import com.example.OCBReporting.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppsService {
    private final AppRepository appRepo;

    public App create(AppRequest app) {
        return appRepo.save(App.builder().name(app.getName()).status("active").build());
    }

    public List<App> all() {
        return appRepo.findAll();
    }
}
