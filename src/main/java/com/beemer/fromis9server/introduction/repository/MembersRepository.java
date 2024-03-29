package com.beemer.fromis9server.introduction.repository;

import com.beemer.fromis9server.introduction.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByName(String name);
}