package com.devpulse.controller;

import com.devpulse.dto.InvoiceRequestDto;
import com.devpulse.dto.InvoiceResponseDto;
import com.devpulse.model.InvoiceStatus;
import com.devpulse.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @RequestBody InvoiceRequestDto request) {
        return ResponseEntity.ok(invoiceService.createInvoice(request));
    }

    @GetMapping ResponseEntity<List<InvoiceResponseDto>> getUserInvoices() {
        return ResponseEntity.ok(invoiceService.getUserInvoices());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InvoiceResponseDto> updateStatus(
            @PathVariable long id,
            @RequestParam InvoiceStatus status) {
        return ResponseEntity.ok(invoiceService.updateStatus(id, status));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

}

