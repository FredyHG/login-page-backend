package dev.fredyhg.loginpagebackend.controllers;

import dev.fredyhg.loginpagebackend.dtos.AuthenticationDto;
import dev.fredyhg.loginpagebackend.models.AuthenticationResponse;
import dev.fredyhg.loginpagebackend.services.AccountService;
import dev.fredyhg.loginpagebackend.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl {

    private final AccountService accountService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDto authenticationDto){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.authenticate(authenticationDto));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        accountService.refreshToken(request, response);
    }
}
