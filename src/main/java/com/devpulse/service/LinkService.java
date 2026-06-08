package com.devpulse.service;

import com.devpulse.dto.LinkRequestDto;
import com.devpulse.dto.LinkResponseDto;
import com.devpulse.model.Link;
import com.devpulse.model.User;
import com.devpulse.repository.LinkRepository;
import com.devpulse.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {
    private LinkRepository linkRepository;
    private UserRepository userRepository;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public LinkResponseDto saveLink(LinkRequestDto request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String normalizedUrl = normalizeUrl(request.getUrl());

        Link link = new Link();
        link.setUrl(normalizedUrl);
        fetchAndSetMetaData(link,normalizedUrl);
        link.setDomain(extractDomain(normalizedUrl));
        link.setUser(user);

        Link saved = linkRepository.save(link);
        return toDto(saved);
    }

    private void fetchAndSetMetaData(Link link, String url) {
        try {
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            String title = doc.title();
            String description = doc.select("meta[name=description]")
                    .attr("content");

            link.setTitle(title.isEmpty() ? url :  title);
            link.setDescription(description.isEmpty() ?
                    "No description available" : description);
        } catch (Exception e) {
            link.setTitle(url);
            link.setDescription("Could not fetch metadata");
        }
    }

    private String normalizeUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("URL cannot be empty");
        }
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            return "https://" + url;
        }
        return url;
    }

    public List<LinkResponseDto> getUserLinks() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return linkRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private String extractDomain(String url) {
        try {
            java.net.URI uri = new java.net.URI(url);
            String host = uri.getHost();
            if (host != null && host.startsWith("www.")) {
                host =  host.substring(4);
            }
            return host;
            } catch (Exception e) {
            return url;
        }

    }

    private LinkResponseDto toDto(Link link) {
        return  new LinkResponseDto(
                link.getId(),
                link.getUrl(),
                link.getTitle(),
                link.getDescription(),
                link.getDomain(),
                link.getCreatedAt()
        );

    }
   public void deleteLink(Long id) {
        User user = getCurrentUser();

        Link link =  linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found"));

  if (!link.getId().equals(user.getId())) {
      throw new RuntimeException("Unauthorized");
  }
  linkRepository.delete(link);
   }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
