package com.devpulse.service;

import com.devpulse.dto.InvoiceRequestDto;
import com.devpulse.dto.InvoiceResponseDto;
import com.devpulse.model.Invoice;
import com.devpulse.model.InvoiceStatus;
import com.devpulse.model.User;
import com.devpulse.repository.InvoiceRepository;
import com.devpulse.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

public InvoiceResponseDto createInvoice(InvoiceRequestDto request) {
    User user = getCurrentUser();

    Invoice invoice = new Invoice();
    invoice.setClientName(request.getClientName());
    invoice.setClientEmail(request.getClientEmail());
    invoice.setAmount(request.getAmount());
    invoice.setDueDate(request.getDueDate());
    invoice.setDescription(request.getDescription());
    invoice.setUser(user);

    Invoice saved = invoiceRepository.save(invoice);
    return toDto(saved);

}

public List<InvoiceResponseDto> getUserInvoices() {
        User user = getCurrentUser();
        return invoiceRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
}

public InvoiceResponseDto updateStatus(Long id, InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        User user = getCurrentUser();
        if (!invoice.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        invoice.setStatus(status);
        return toDto(invoiceRepository.save(invoice));
}

private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
      return  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
private InvoiceResponseDto toDto(Invoice invoice) {
        return new InvoiceResponseDto(
                invoice.getId(),
                invoice.getClientName(),
                invoice.getClientEmail(),
                invoice.getAmount(),
                invoice.getDueDate(),
                invoice.getStatus(),
                invoice.getDescription(),
                invoice.getCreatedAt()
        );
}
}
