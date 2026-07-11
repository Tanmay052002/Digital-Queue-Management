package com.queuemgmt.repository;

import com.queuemgmt.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByOfficeId(Long officeId);
}
