package com.liveincident.auth.Controller;

import com.liveincident.auth.Service.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class JwtTestController {

    private final JwtService jwtService;

    public JwtTestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public String generateToken() {
        return jwtService.generateAccessToken(
                "user-123",
                "LEAD",
                "team-101"
        );
    }

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "You are authenticated with a valid JWT!";
    }
}