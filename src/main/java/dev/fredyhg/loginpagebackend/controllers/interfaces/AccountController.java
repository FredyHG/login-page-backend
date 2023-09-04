package dev.fredyhg.loginpagebackend.controllers.interfaces;

import dev.fredyhg.loginpagebackend.dtos.AccountDataDto;
import dev.fredyhg.loginpagebackend.dtos.AccountPostRequest;
import dev.fredyhg.loginpagebackend.dtos.AccountUpdateInfoDto;
import dev.fredyhg.loginpagebackend.models.AuthenticationResponse;
import dev.fredyhg.loginpagebackend.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.sql.ast.tree.expression.Summarization;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AccountController {

    @Operation(summary = "Create new account", description = "To perform the request, it is necessary to have the permission of (USER)", tags = {"USER"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Email already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<AuthenticationResponse> createAccount(AccountPostRequest accountPostRequest);

    @Operation(summary = "Delete account", description = "To perform the request, it is necessary to have the permission of (USER OR ADMIN),", tags = {"USER", "ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Account not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> deleteAccount(HttpServletRequest request,
                                                  HttpServletResponse response);

    @Operation(summary = "Getting account data", description = "To perform the request, it is necessary to have the permission of (USER OR ADMIN)", tags = {"USER", "ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This method is responsible for retrieving the data associated with the current user's account."),
            @ApiResponse(responseCode = "400", description = "Account not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<AccountDataDto> getCurrentAccountData(HttpServletRequest request,
                                                         HttpServletResponse response);

    @Operation(summary = "Update account data", description = "To perform the request, it is necessary to have the permission of (USER OR ADMIN)", tags = {"USER", "ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This method is responsible for update the data associated with the current user's account."),
            @ApiResponse(responseCode = "400", description = "Account not found or Invalid Token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> updateAccountData(AccountUpdateInfoDto accountUpdateInfoDto);

}
