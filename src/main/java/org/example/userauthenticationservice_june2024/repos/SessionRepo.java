package org.example.userauthenticationservice_june2024.repos;

import org.example.userauthenticationservice_june2024.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<Session,Long> {
    Optional<Session> findByToken(String token);
    Optional<Session> findByTokenAndUser_Id(String token,Long userId);
}
