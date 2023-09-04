package dev.fredyhg.loginpagebackend.repositories;

import dev.fredyhg.loginpagebackend.models.AccountModel;
import dev.fredyhg.loginpagebackend.utils.mappers.AccountMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, UUID> {

    Optional<AccountModel> findByEmail(String email);

}
