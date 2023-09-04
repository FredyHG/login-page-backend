package dev.fredyhg.loginpagebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fredyhg.loginpagebackend.dtos.AccountDataDto;
import dev.fredyhg.loginpagebackend.dtos.AccountPostRequest;
import dev.fredyhg.loginpagebackend.dtos.AccountUpdateInfoDto;
import dev.fredyhg.loginpagebackend.dtos.AuthenticationDto;
import dev.fredyhg.loginpagebackend.exceptions.AccountException;
import dev.fredyhg.loginpagebackend.models.AccountModel;
import dev.fredyhg.loginpagebackend.models.AuthenticationResponse;
import dev.fredyhg.loginpagebackend.repositories.AccountRepository;
import dev.fredyhg.loginpagebackend.security.token.Token;
import dev.fredyhg.loginpagebackend.security.token.TokenRepository;
import dev.fredyhg.loginpagebackend.security.token.TokenType;
import dev.fredyhg.loginpagebackend.utils.mappers.AccountMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse createAccount(AccountPostRequest accountPostRequest){

        Optional<AccountModel> accountExists = accountRepository.findByEmail(accountPostRequest.getEmail());

        if(accountExists.isPresent()){
            throw new AccountException("Email already registered");
        }

        AccountModel account = AccountMapper.accountPostToAccountModel(accountPostRequest);

        account.setPassword(passwordEncoder.encode(accountPostRequest.getPassword()));

        var savedAccount = accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        var refreshToken = jwtService.generatedRefreshToken(account);
        saveUserToken(savedAccount, jwtToken);


        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }




    public AccountDataDto getAccountData(HttpServletRequest request,
                                         HttpServletResponse response){


        return AccountMapper.accountModelToAccountDataDto(accountExistsByToken(request));
    }

    @Transactional
    public void deleteAccount(HttpServletRequest request,
                              HttpServletResponse response){

        accountRepository.delete(accountExistsByToken(request));
    }

    @Transactional
    public void updateAccountData(AccountUpdateInfoDto account) {
        accountExistsById(account.getId());

        AccountModel accountModel = AccountMapper.accountUpdateDtoToAccountModel(account);
        accountRepository.save(accountModel);
    }


    private void revokeAllUserTokens(AccountModel account){
        var validAccountTokens = tokenRepository.findAllValidTokenByUser(account.getId());

        if(validAccountTokens.isEmpty()) return;

        validAccountTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validAccountTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String accountEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        accountEmail = jwtService.extractUsername(refreshToken);

        if(accountEmail != null){
            var account = this.accountRepository.findByEmail(accountEmail)
                    .orElseThrow();

            if(jwtService.isTokenValid(refreshToken, account)){
                var accessToken = jwtService.generateToken(account);
                revokeAllUserTokens(account);
                saveUserToken(account, accessToken);

                var authResponse = AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                } catch (IOException ex) {
                    throw new InternalAuthenticationServiceException("Error");
                }
            }
        }
    }

    public AuthenticationResponse authenticate(AuthenticationDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var usuario = accountRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generatedRefreshToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AccountModel accountExistsById(UUID id){

        Optional<AccountModel> accountExists = accountRepository.findById(id);

        if(accountExists.isEmpty()) {
            throw new AccountException("Account not exists");
        }

        return accountExists.get();
    }

    public AccountModel accountExistsByToken(HttpServletRequest request){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AccountException("Invalid Token");
        }

        String accountEmail = jwtService.extractUsername(authHeader.substring(7));

        Optional<AccountModel> accountExists = accountRepository.findByEmail(accountEmail);

        if(accountExists.isEmpty()) {
            throw new AccountException("Account not exists");
        }

        return accountExists.get();
    }

    public AccountModel accountExistsByEmail(String email){

        Optional<AccountModel> accountExists = accountRepository.findByEmail(email);

        if(accountExists.isEmpty()) {
            throw new AccountException("Account not exists");
        }

        return accountExists.get();
    }

    private void saveUserToken(AccountModel account, String jwtToken) {
        var token = Token.builder()
                .account(account)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }





}
