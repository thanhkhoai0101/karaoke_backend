package com.karaoke.karaoke_backend.repository;

import com.karaoke.karaoke_backend.model.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {
    Optional<LoginSession> findByToken(String rawToken);

}
