package com.devpulse.repository;

import com.devpulse.model.Invoice;
import com.devpulse.model.InvoiceStatus;
import com.devpulse.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUser(User user);
    List<Invoice> findByUserAndStatus(User user, InvoiceStatus status);

}
