package com.queuemgmt.repository;

import com.queuemgmt.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByOfficeId(Long officeId);
}
