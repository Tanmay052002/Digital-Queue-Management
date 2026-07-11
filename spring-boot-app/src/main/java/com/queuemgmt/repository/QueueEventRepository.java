package com.queuemgmt.repository;

import com.queuemgmt.model.QueueEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueEventRepository extends JpaRepository<QueueEvent, Long> {
}
