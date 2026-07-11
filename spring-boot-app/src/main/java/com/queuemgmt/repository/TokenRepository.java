package com.queuemgmt.repository;

import com.queuemgmt.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByCounterIdAndStatus(Long counterId, String status);

    List<Token> findByServiceId(Long serviceId);

    long countByCounterIdAndStatus(Long counterId, String status);
}
