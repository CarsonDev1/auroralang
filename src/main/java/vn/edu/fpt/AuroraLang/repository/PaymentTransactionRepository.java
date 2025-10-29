package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.PaymentTransaction;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    Optional<PaymentTransaction> findByTransactionCode(String transactionCode);
    Page<PaymentTransaction> findByUser_UserId(Integer userId, Pageable pageable);
    Page<PaymentTransaction> findByUser_UserIdAndStatus(Integer userId, PaymentTransaction.Status status, Pageable pageable);
    Page<PaymentTransaction> findByStatus(PaymentTransaction.Status status, Pageable pageable);
}

