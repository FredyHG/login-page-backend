package dev.fredyhg.loginpagebackend.utils.mappers;

import dev.fredyhg.loginpagebackend.dtos.AccountDataDto;
import dev.fredyhg.loginpagebackend.dtos.AccountPostRequest;
import dev.fredyhg.loginpagebackend.dtos.AccountUpdateInfoDto;
import dev.fredyhg.loginpagebackend.models.AccountModel;
import dev.fredyhg.loginpagebackend.security.Role;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static AccountDataDto accountModelToAccountDataDto(AccountModel accountModel){

        return AccountDataDto
                .builder()
                .name(accountModel.getName())
                .email(accountModel.getEmail())
                .state(accountModel.getState())
                .surname(accountModel.getSurname())
                .logradouro(accountModel.getLogradouro())
                .city(accountModel.getCity())
                .build();

    }

    public static AccountModel accountPostToAccountModel(AccountPostRequest accountPostRequest) {



        return AccountModel
                .builder()
                .name(accountPostRequest.getName())
                .surname(accountPostRequest.getSurname())
                .email(accountPostRequest.getEmail())
                .role(Role.ROLE_USER)
                .state(accountPostRequest.getState())
                .city(accountPostRequest.getCity())
                .logradouro(accountPostRequest.getLogradouro())
                .build();
    }


    public static AccountModel accountUpdateDtoToAccountModel(AccountUpdateInfoDto accountUpdateInfoDto){
        return AccountModel
                .builder()
                .id(accountUpdateInfoDto.getId())
                .name(accountUpdateInfoDto.getName())
                .email(accountUpdateInfoDto.getEmail())
                .password(accountUpdateInfoDto.getPassword())
                .state(accountUpdateInfoDto.getState())
                .city(accountUpdateInfoDto.getCity())
                .email(accountUpdateInfoDto.getEmail())
                .build();
    }


}
