package com.devpulse.repository;

import com.devpulse.model.Link;
import com.devpulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByUser(User user);
}
