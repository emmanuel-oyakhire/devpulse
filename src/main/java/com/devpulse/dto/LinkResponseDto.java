package com.devpulse.dto;

import java.time.LocalDateTime;

public class LinkResponseDto {
    private Long id;
    private String url;
    private String title;
    private String description;
    private String domain;
    private LocalDateTime createAt;

    public LinkResponseDto(Long id, String url, String title, String description,
                           String domain, LocalDateTime createAt) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.description = description;
        this.domain = domain;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDomain() {
        return domain;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
