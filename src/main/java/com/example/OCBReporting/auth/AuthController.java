package com.example.OCBReporting.auth;

import com.example.OCBReporting.auth.dto.LoginResponse;
import com.example.OCBReporting.auth.dto.AuthCredentials;
import com.example.OCBReporting.auth.dto.UserRequest;
import com.example.OCBReporting.dto.BasicResponse;
import com.example.OCBReporting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check-token")
    public ResponseEntity<Boolean> checkToken() {
        return ResponseEntity.ok(
                true
        );
    }

    @PostMapping("/register")
    public ResponseEntity<BasicResponse<User>> register(
            @RequestBody UserRequest user
    ) {
        try {
            return ResponseEntity.ok(
                    BasicResponse.<User>builder()
                            .data(authService.register(user))
                            .build()
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    BasicResponse.<User>builder()
                            .error(e.getMessage())
                            .build(), HttpStatus.BAD_REQUEST
            );

        }
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponse<LoginResponse>> login(
            @RequestBody AuthCredentials authCredentials
    ) {
        return ResponseEntity.ok(
                BasicResponse.<LoginResponse>builder().data(authService.login(authCredentials)).build()
        );
    }

}
