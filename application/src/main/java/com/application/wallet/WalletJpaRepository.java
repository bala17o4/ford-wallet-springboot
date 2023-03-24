package com.application.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WalletJpaRepository extends JpaRepository<WalletDto,Integer>{
    WalletDto findByName(String name);
}
