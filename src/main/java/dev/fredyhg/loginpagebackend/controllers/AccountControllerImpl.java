package dev.fredyhg.loginpagebackend.controllers;

import dev.fredyhg.loginpagebackend.controllers.interfaces.AccountController;
import dev.fredyhg.loginpagebackend.dtos.AccountDataDto;
import dev.fredyhg.loginpagebackend.dtos.AccountPostRequest;
import dev.fredyhg.loginpagebackend.dtos.AccountUpdateInfoDto;
import dev.fredyhg.loginpagebackend.models.AuthenticationResponse;
import dev.fredyhg.loginpagebackend.models.ResponseMessage;
import dev.fredyhg.loginpagebackend.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthenticationResponse> createAccount(@RequestBody @Valid AccountPostRequest accountPostRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountPostRequest));
    }

    @DeleteMapping
    @Override
    public ResponseEntity<ResponseMessage> deleteAccount(HttpServletRequest request,
                                                         HttpServletResponse response) {

        accountService.deleteAccount(request, response);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Account deleted successfully"));
    }

    @GetMapping("/data")
    @Override
    public ResponseEntity<AccountDataDto> getCurrentAccountData(HttpServletRequest request,
                                                                HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountData(request, response));
    }

    @PutMapping("/data")
    @Override
    public ResponseEntity<ResponseMessage> updateAccountData(@RequestBody AccountUpdateInfoDto accountUpdateInfoDto) {

        accountService.updateAccountData(accountUpdateInfoDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Account data updated successfully"));
    }




}
