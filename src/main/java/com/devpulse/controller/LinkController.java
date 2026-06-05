package com.devpulse.controller;

import com.devpulse.dto.LinkRequestDto;
import com.devpulse.dto.LinkResponseDto;
import com.devpulse.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<LinkResponseDto> saveLink(
            @RequestBody LinkRequestDto request) {
       return ResponseEntity.ok(linkService.saveLink(request));
    }

    @GetMapping
    public ResponseEntity<List<LinkResponseDto>> getUserLinks() {
        return ResponseEntity.ok(linkService.getUserLinks());
    }
}
