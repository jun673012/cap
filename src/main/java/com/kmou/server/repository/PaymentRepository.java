package com.kmou.server.repository;

import com.kmou.server.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByOrderId(String orderId);
}
