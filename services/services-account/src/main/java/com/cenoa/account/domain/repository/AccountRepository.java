package com.cenoa.account.domain.repository;

import com.cenoa.account.domain.entity.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByDeletedIsFalseAndUserUuid(UUID userUuid, Sort sort);
    Optional<Account> findByUuid(UUID accountUuid);

}
