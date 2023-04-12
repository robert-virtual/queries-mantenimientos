package com.example.queriesmantenimientos.apps;

import com.example.queriesmantenimientos.apps.dto.AppRequest;
import com.example.queriesmantenimientos.dto.BasicResponse;
import com.example.queriesmantenimientos.model.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("apps")
@RequiredArgsConstructor
public class AppsController {
    private final AppsService appsService;

    @GetMapping("all")
    public ResponseEntity<BasicResponse<List<App>>> create(
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<List<App>>builder()
                        .data(appsService.all())
                        .build()
        );
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse<App>> create(
            @RequestBody AppRequest app
    ) {
        return ResponseEntity.ok(
                BasicResponse
                        .<App>builder()
                        .data(appsService.create(app))
                        .build()
        );
    }
}
