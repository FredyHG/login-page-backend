package dev.fredyhg.loginpagebackend.controllers.interfaces;

import dev.fredyhg.loginpagebackend.dtos.AuthenticationDto;
import dev.fredyhg.loginpagebackend.models.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {

    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDto authenticationDto);

}
